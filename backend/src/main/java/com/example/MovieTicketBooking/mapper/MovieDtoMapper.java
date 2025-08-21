package com.example.MovieTicketBooking.mapper;

import org.springframework.stereotype.Component;

import com.example.MovieTicketBooking.dto.MovieDto;
import com.example.MovieTicketBooking.model.Movie;

@Component
public class MovieDtoMapper {
    
    public Movie dtoToMovie(MovieDto movieDto){
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setDuration(movieDto.getDuration());
        return movie;
    }
}
