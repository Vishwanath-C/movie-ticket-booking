package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.responsedtos.MovieShowResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.MovieShowResponseDtoMapper;
import com.example.MovieTicketBooking.model.MovieAssignment;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.ShowSeat;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.repository.MovieShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieShowService
{

    @Autowired
    MovieShowRepository movieShowRepository;

    @Autowired
    MovieAssignmentService movieAssignmentService;

    @Autowired
    MovieShowResponseDtoMapper movieShowResponseDtoMapper;

    public MovieShow createMovieShow(MovieShow movieShow, Theatre theatre) {
        List<ShowSeat> showSeats = theatre.getSeats().stream()
                .map(seat -> ShowSeat.builder()
                        .movieShow(movieShow)
                        .seat(seat)
                        .price(seat.getPrice())
                        .booked(false)
                        .build())
                .toList();

        movieShow.setShowSeats(showSeats);
        return movieShowRepository.save(movieShow);
    }

    public MovieShow getMovieShowById(Long id) {
        return movieShowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MovieShow with Id " + id + " not found!"));
    }

    public List<MovieShow> getMovieShowsByAssignment(MovieAssignment movieAssignment) {
        return movieShowRepository.findMovieShowsByMovieAssignment(movieAssignment);
    }

    public List<List<MovieShowResponseDto>> getMovieShowsByMovieId(Long movieId) {
        List<MovieAssignment> movieAssignments = movieAssignmentService.getMovieAssignments(movieId);
        List<List<MovieShowResponseDto>> response = new ArrayList<>();

        for (MovieAssignment movieAssignment : movieAssignments) {
            response.add(new ArrayList<>());

            List<MovieShow> movieShows = getMovieShowsByAssignment(movieAssignment);

            for (MovieShow movieShow : movieShows) {
                MovieShowResponseDto dto = movieShowResponseDtoMapper.convertToMovieShowResponseDto(movieShow);
                response.get(response.size() - 1).add(dto);
            }
        }
        return response;
    }

    public List<List<MovieShowResponseDto>> getMovieShowsByMovieIdByDate(Long movieId, LocalDate date) {
        List<MovieAssignment> movieAssignments = movieAssignmentService.getMovieAssignments(movieId);
        List<List<MovieShowResponseDto>> response = new ArrayList<>();

        for (MovieAssignment movieAssignment : movieAssignments) {
            response.add(new ArrayList<>());

            List<MovieShow> movieShows = getMovieShowsByAssignment(movieAssignment);

            for (MovieShow movieShow : movieShows) {
                if (movieShow.getShowDate().isEqual(date)) {
                    MovieShowResponseDto dto = movieShowResponseDtoMapper.convertToMovieShowResponseDto(movieShow);
                    response.get(response.size() - 1).add(dto);
                }
            }
        }
        return response;
    }
}
