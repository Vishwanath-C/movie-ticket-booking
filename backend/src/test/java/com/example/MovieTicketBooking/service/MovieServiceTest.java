package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.MovieDtoMapper;
import com.example.MovieTicketBooking.mapper.MovieResponseDtoMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieDtoMapper movieDtoMapper;

    @Mock
    private MovieResponseDtoMapper movieResponseDtoMapper;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
    }

    @Test
    void createMovie_ShouldSaveMovie_WhenNotExists() {
        MovieRequestDto requestDto = new MovieRequestDto();
        requestDto.setTitle("New Movie");

        when(movieRepository.findByTitle("New Movie")).thenReturn(Optional.empty());
        when(movieDtoMapper.dtoToMovie(requestDto)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie saved = movieService.createMovie(requestDto);

        assertNotNull(saved);
        assertEquals("Test Movie", saved.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void createMovie_ShouldThrowException_WhenMovieAlreadyExists() {
        MovieRequestDto requestDto = new MovieRequestDto();
        requestDto.setTitle("Test Movie");

        when(movieRepository.findByTitle("Test Movie")).thenReturn(Optional.of(movie));

        assertThrows(IllegalArgumentException.class, () -> movieService.createMovie(requestDto));
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void getMovieById_ShouldReturnMovie_WhenExists() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void getMovieById_ShouldThrowException_WhenNotExists() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById(1L));
    }

    @Test
    void getAllMovies_ShouldReturnDtos() {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(1L);
        dto.setTitle("Test Movie");

        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(movieResponseDtoMapper.movieToDto(movie)).thenReturn(dto);

        List<MovieResponseDto> result = movieService.getAllMovies();

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
    }

    @Test
    void getMoviesWithShows_ShouldReturnMoviesWithActiveSchedules() {
        LocalDate today = LocalDate.now();
        MovieSchedule schedule = new MovieSchedule();
        schedule.setStartDate(today.minusDays(1));
        schedule.setEndDate(today.plusDays(1));

        movie.setMovieSchedules(List.of(schedule));

        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(1L);
        dto.setTitle("Test Movie");

        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(movieResponseDtoMapper.movieToDto(movie)).thenReturn(dto);

        List<MovieResponseDto> result = movieService.getMoviesWithShows();

        assertEquals(1, result.size());
    }

    @Test
    void getByTitle_ShouldReturnMovie_WhenExists() {
        when(movieRepository.findByTitle("Test Movie")).thenReturn(Optional.of(movie));

        Movie result = movieService.getByTitle("Test Movie");

        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void getByTitle_ShouldThrowException_WhenNotExists() {
        when(movieRepository.findByTitle("Unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getByTitle("Unknown"));
    }

    @Test
    void deleteMovie_ShouldDelete_WhenExists() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(1L);

        verify(movieRepository, times(1)).delete(movie);
    }
}
