package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.responsedtos.MovieShowResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.MovieShowResponseDtoMapper;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.ShowSeat;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.repository.MovieShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieShowService
{
    private final  TheatreService theatreService;
    private final   MovieShowRepository movieShowRepository;
    private final  MovieScheduleService movieScheduleService;
    private final  MovieShowResponseDtoMapper movieShowResponseDtoMapper;

    /**
     * Creates a new movie show.
     *
     * @param movieShow the movie show to be created
     * @param theatre   the theatre where the movie is being shown
     * @return the saved movie show
     */
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

    /**
     * Get a movie show by ID.
     *
     * @param id the ID of the movie show
     * @return the movie show
     */
    public MovieShow getMovieShowById(Long id) {
        return movieShowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MovieShow with Id " + id + " not found!"));
    }

    /**
     * Get all movie shows for a specific movie schedule.
     *
     * @param movieSchedule the movie schedule
     * @return list of movie shows for the given schedule
     */
    public List<MovieShow> getMovieShowsBySchedule(MovieSchedule movieSchedule) {
        return movieShowRepository.findAllByMovieSchedule(movieSchedule);
    }

    /**
     * Get movie shows by movie ID.
     *
     * @param movieId the ID of the movie
     * @return list of lists of movie show response DTOs
     */
    public List<List<MovieShowResponseDto>> getMovieShowsByMovieId(Long movieId) {
        List<MovieSchedule> movieSchedules = movieScheduleService.getMovieSchedules(movieId);
        List<List<MovieShowResponseDto>> response = new ArrayList<>();

        for (MovieSchedule movieSchedule : movieSchedules) {
            response.add(new ArrayList<>());
            List<MovieShow> movieShows = getMovieShowsBySchedule(movieSchedule);

            for (MovieShow movieShow : movieShows) {
                MovieShowResponseDto dto = movieShowResponseDtoMapper.convertToMovieShowResponseDto(movieShow);
                response.get(response.size() - 1).add(dto);
            }
        }
        return response;
    }

    /**
     * Get movie shows by movie ID and date.
     *
     * @param movieId the ID of the movie
     * @param date    the date of the show
     * @return list of lists of movie show response DTOs
     */
    public List<List<MovieShowResponseDto>> getMovieShowsByMovieIdByDate(Long movieId, LocalDate date) {
        List<MovieSchedule> movieSchedules = movieScheduleService.getMovieSchedules(movieId);
        List<List<MovieShowResponseDto>> response = new ArrayList<>();

        for (MovieSchedule movieSchedule : movieSchedules) {
            response.add(new ArrayList<>());
            List<MovieShow> movieShows = getMovieShowsBySchedule(movieSchedule);

            for (MovieShow movieShow : movieShows) {
                if (movieShow.getShowDate().isEqual(date)) {
                    MovieShowResponseDto dto = movieShowResponseDtoMapper.convertToMovieShowResponseDto(movieShow);
                    response.get(response.size() - 1).add(dto);
                }
            }
        }
        return response;
    }

    /**
     * Get all available dates for a movie within a specific number of days.
     *
     * @param movieId the ID of the movie
     * @param days    the number of days to check for available shows
     * @return list of available dates
     */
    public List<LocalDate> getAvailableDates(Long movieId, int days) {
        List<MovieSchedule> movieSchedules = movieScheduleService.getMovieSchedules(movieId);


        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        Set<LocalDate> availableDates = new HashSet<>();

        for (MovieSchedule movieSchedule : movieSchedules) {
            List<MovieShow> movieShows = movieSchedule.getMovieShows();

            for (MovieShow movieShow : movieShows) {
                if (!movieShow.getShowDate().isBefore(today) && !movieShow.getShowDate().isAfter(endDate)) {
                    availableDates.add(movieShow.getShowDate());
                }
            }
        }

        List<LocalDate> result = new ArrayList<>(availableDates);
        Collections.sort(result);
        return result;
    }
}
