package com.example.MovieTicketBooking.mapper;

import org.springframework.stereotype.Component;

import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.model.MovieSchedule;

@Component
public class MovieScheduleResponseMapper
{
    public MovieScheduleResponseDto convertTResponseDto(MovieSchedule movieSchedule) {
        return MovieScheduleResponseDto.builder()
                .id(movieSchedule.getId())
                .movieTitle(movieSchedule.getMovie().getTitle())
                .theatreName(movieSchedule.getTheatre().getName())
                .startDate(movieSchedule.getStartDate())
                .endDate(movieSchedule.getEndDate())
                .build();
    }
}
