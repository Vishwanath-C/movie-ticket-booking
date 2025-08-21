package com.example.MovieTicketBooking.mapper;

import org.springframework.stereotype.Component;

import com.example.MovieTicketBooking.dto.responsedtos.MovieAssignmentResponseDto;
import com.example.MovieTicketBooking.model.MovieAssignment;

@Component
public class MovieAssignmentResponseMapper {
    public MovieAssignmentResponseDto convertTResponseDto(MovieAssignment movieAssignment) {
        return MovieAssignmentResponseDto.builder()
                .id(movieAssignment.getId())
                .movie(movieAssignment.getMovie().getTitle())
                .theatre(movieAssignment.getTheatre().getName())
                .startDate(movieAssignment.getStartDate())
                .endDate(movieAssignment.getEndDate())
                .build();
    }
}
