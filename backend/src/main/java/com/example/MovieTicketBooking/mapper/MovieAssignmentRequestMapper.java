package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.requestdtos.MovieAssignmentRequestDto;
import com.example.MovieTicketBooking.model.MovieAssignment;
import com.example.MovieTicketBooking.service.MovieService;
import com.example.MovieTicketBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MovieAssignmentRequestMapper {

    private final MovieService movieService;
    private final TheatreService theatreService;

    public MovieAssignment convertToMovieAssignment(MovieAssignmentRequestDto dto) {
        return MovieAssignment.builder()
                .movie(movieService.getMovieById(dto.getMovieId()))
                .theatre(theatreService.getTheatreById(dto.getTheatreId()))
                .movieShows(new ArrayList<>())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
