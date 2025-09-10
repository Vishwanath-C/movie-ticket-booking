package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.mapper.MovieDtoMapper;
import com.example.MovieTicketBooking.mapper.MovieResponseDtoMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MovieServiceTest
{
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieDtoMapper movieDtoMapper;

    @Mock
    private MovieResponseDtoMapper movieResponseDtoMapper;

    @InjectMocks
    private  MovieService movieService;

    private Movie movie;
    private MovieRequestDto movieRequestDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        movie = Movie.builder().id(1L).title("KGF").description("Action").duration(100).build();

       movieRequestDto = MovieRequestDto.builder().title("KGF").description("Action").duration(150).build();
    }

    @Test
    void testCreateMovie(){
        when(movieDtoMapper.dtoToMovie(movieRequestDto)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.createMovie(movieRequestDto);

        assertNotNull(result);
        assertEquals("KGF", result.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testGetMovieById(){
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals("KGF", result.getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllMovies(){
        List<Movie> movies = new ArrayList<>();
        Movie movieKGF = Movie.builder().id(1L).title("KGF").description("Action").duration(150).build();
        Movie movieSuFromSo = Movie.builder().id(2L).title("Su from so").description("Comedy").duration(120).build();

        movies.add(movieKGF);
        movies.add(movieSuFromSo);

        when(movieRepository.findAll()).thenReturn(movies);

        MovieResponseDto dtoKFG = MovieResponseDto.builder().id(1L).title("KGF").description("Action").durationMinutes(150).build();
        MovieResponseDto dtoSuFromSo = MovieResponseDto.builder().id(2L).title("Su from so").description("Comedy").durationMinutes(120).build();

        when(movieResponseDtoMapper.movieToDto(movieKGF)).thenReturn(dtoKFG);
        when(movieResponseDtoMapper.movieToDto(movieSuFromSo)).thenReturn(dtoSuFromSo);

        List<MovieResponseDto> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        assertEquals("KGF", result.get(0).getTitle());
        assertEquals("Su from so", result.get(1).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetByTitle(){
        when(movieRepository.findByTitle("KGF")).thenReturn(Optional.ofNullable(movie));

        Movie result = movieService.getByTitle("KGF");

        assertNotNull(result);
        assertEquals("KGF", result.getTitle());
        verify(movieRepository, times(1)).findByTitle("KGF");
    }


}
