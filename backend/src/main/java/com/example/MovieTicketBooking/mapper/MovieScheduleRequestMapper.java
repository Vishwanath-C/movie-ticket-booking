package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.service.MovieService;
import com.example.MovieTicketBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MovieScheduleRequestMapper
{

    private final MovieService movieService;
    private final TheatreService theatreService;

    public MovieSchedule convertToMovieSchedule(MovieScheduleRequestDto dto) {
        return MovieSchedule.builder()
                .movie(movieService.getMovieById(dto.getMovieId()))
                .theatre(theatreService.getTheatreEntityById(dto.getTheatreId()))
                .movieShows(new ArrayList<>())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
