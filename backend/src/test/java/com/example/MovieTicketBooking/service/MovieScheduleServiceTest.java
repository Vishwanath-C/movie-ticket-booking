package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.exception.InvalidDateException;
import com.example.MovieTicketBooking.mapper.MovieScheduleRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieScheduleResponseMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.repository.MovieScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieScheduleServiceTest {

    @Mock
    private MovieScheduleRepository movieScheduleRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private TheatreService theatreService;

    @Mock
    private MovieScheduleRequestMapper requestMapper;

    @Mock
    private MovieScheduleResponseMapper responseMapper;

    @InjectMocks
    private MovieScheduleService movieScheduleService;

    private MovieScheduleRequestDto requestDto;
    private MovieSchedule schedule;
    private Movie movie;
    private Theatre theatre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new MovieScheduleRequestDto();
        requestDto.setStartDate(LocalDate.now());
        requestDto.setEndDate(LocalDate.now().plusDays(5));

        schedule = new MovieSchedule();
        schedule.setId(1L);

        movie = new Movie();
        movie.setId(10L);
        movie.setTitle("Test Movie");

        theatre = new Theatre();
        theatre.setId(20L);
        theatre.setName("Test Theatre");
    }

    @Test
    void createMovieSchedule_ShouldSaveAndReturnResponse() {
        MovieScheduleResponseDto responseDto = new MovieScheduleResponseDto();
        responseDto.setId(1L);

        when(requestMapper.convertToMovieSchedule(requestDto)).thenReturn(schedule);
        when(movieScheduleRepository.save(schedule)).thenReturn(schedule);
        when(responseMapper.convertTResponseDto(schedule)).thenReturn(responseDto);

        MovieScheduleResponseDto result = movieScheduleService.createMovieSchedule(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(movieScheduleRepository, times(1)).save(schedule);
    }

    @Test
    void createMovieSchedule_ShouldThrowException_WhenEndDateBeforeStartDate() {
        requestDto.setEndDate(LocalDate.now().minusDays(1));

        assertThrows(InvalidDateException.class, () -> movieScheduleService.createMovieSchedule(requestDto));
        verify(movieScheduleRepository, never()).save(any());
    }

    @Test
    void getScheduleById_ShouldReturnSchedule_WhenExists() {
        when(movieScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        MovieSchedule result = movieScheduleService.getScheduleById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getScheduleById_ShouldThrowException_WhenNotFound() {
        when(movieScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceAccessException.class, () -> movieScheduleService.getScheduleById(1L));
    }

    @Test
    void getMovieSchedules_ShouldReturnSchedules() {
        when(movieService.getMovieById(10L)).thenReturn(movie);
        when(movieScheduleRepository.findAllByMovie(movie)).thenReturn(List.of(schedule));

        List<MovieSchedule> result = movieScheduleService.getMovieSchedules(10L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getMovieSchedulesByTheatre_ShouldReturnResponse_WhenExists() {
        schedule.setMovie(movie);
        schedule.setTheatre(theatre);

        when(theatreService.getTheatreEntityById(20L)).thenReturn(theatre);
        when(movieScheduleRepository.findAllByTheatre(theatre)).thenReturn(List.of(schedule));

        MovieScheduleResponseDto result = movieScheduleService.getMovieSchedulesByTheatre(20L);

        assertNotNull(result);
        assertEquals("Test Movie", result.getMovieTitle());
        assertEquals("Test Theatre", result.getTheatreName());
    }

    @Test
    void getMovieSchedulesByTheatre_ShouldThrowException_WhenNotFound() {
        when(theatreService.getTheatreEntityById(20L)).thenReturn(theatre);
        when(movieScheduleRepository.findAllByTheatre(theatre)).thenReturn(List.of());

        assertThrows(ResourceAccessException.class, () -> movieScheduleService.getMovieSchedulesByTheatre(20L));
    }
}
