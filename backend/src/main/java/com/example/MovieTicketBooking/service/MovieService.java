package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.MovieDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.MovieDtoMapper;
import com.example.MovieTicketBooking.mapper.MovieResponseDtoMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieDtoMapper movieDtoMapper;

    @Autowired
    MovieResponseDtoMapper movieResponseDtoMapper;

    public Movie createMovie(MovieDto movieDto){
        Movie movie = movieDtoMapper.dtoToMovie(movieDto);
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found!"));
    }


    public List<MovieResponseDto> getAllMovies(){
        List<Movie> movies = movieRepository.findAll();

        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();

        for(Movie movie : movies){
            movieResponseDtos.add(movieResponseDtoMapper.movieToDto(movie));
        }
        return movieResponseDtos;
    }

    public Movie getByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public void deleteMovie(Long id){
        movieRepository.delete(getMovieById(id));
    }

}
