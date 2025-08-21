package com.example.MovieTicketBooking.mapper;

import org.springframework.stereotype.Component;

import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.model.Movie;

@Component
public class MovieResponseDtoMapper {
    public MovieResponseDto movieToDto(Movie movie) {
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .build();

        return movieResponseDto;
    }
}
