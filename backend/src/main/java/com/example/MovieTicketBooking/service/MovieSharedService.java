package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieAssignmentRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieAssignmentResponseDto;
import com.example.MovieTicketBooking.mapper.MovieAssignmentRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieAssignmentResponseMapper;
import com.example.MovieTicketBooking.model.MovieAssignment;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class MovieSharedService
{
    @Autowired
    MovieAssignmentService movieAssignmentService;

    @Autowired
    MovieShowService movieShowService;

    @Autowired
    MovieAssignmentRequestMapper requestMapper;

    @Autowired
    MovieAssignmentResponseMapper responseMapper;

    @Autowired
    TheatreService theatreService;

    @Transactional
    public MovieAssignmentResponseDto createMovieShows(MovieAssignmentRequestDto dto) {
        MovieAssignmentResponseDto movieAssignmentResponseDto = movieAssignmentService.createMovieAssignment(dto);
        MovieAssignment movieAssignment = movieAssignmentService.getAssignmentById(movieAssignmentResponseDto.getId());
        long numberOfDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        LocalDate currentDate = dto.getStartDate();
        Theatre theatre = theatreService.getTheatreById(dto.getTheatreId());


        for (int i = 0; i < numberOfDays; i++) {
            for (int j = 0; j < dto.getNumberOfShowsPerDay(); j++) {
                MovieShow movieShow = MovieShow.builder()
                        .showTime(dto.getShowTimings().get(j))
                        .showDate(currentDate)
                        .movieAssignment(movieAssignment)
                        .availableSeatsCount(movieAssignment.getTheatre().getSeats().size())
                        .build();
                movieShowService.createMovieShow(movieShow, theatre);
            }
            currentDate = currentDate.plusDays(1);
        }
        return responseMapper.convertTResponseDto(movieAssignment);
    }

}
