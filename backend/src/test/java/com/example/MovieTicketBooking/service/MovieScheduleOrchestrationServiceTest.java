package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.mapper.MovieScheduleRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieScheduleResponseMapper;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieScheduleOrchestrationServiceTest {

    @Mock
    private MovieScheduleService movieScheduleService;

    @Mock
    private MovieShowService movieShowService;

    @Mock
    private MovieScheduleRequestMapper requestMapper;

    @Mock
    private MovieScheduleResponseMapper responseMapper;

    @Mock
    private TheatreService theatreService;

    @InjectMocks
    private MovieScheduleOrchestrationService orchestrationService;

    private MovieScheduleRequestDto requestDto;
    private MovieSchedule movieSchedule;
    private MovieScheduleResponseDto scheduleResponseDto;
    private Theatre theatre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new MovieScheduleRequestDto();
        requestDto.setStartDate(LocalDate.of(2025, 9, 1));
        requestDto.setEndDate(LocalDate.of(2025, 9, 3));  // 3 days
        requestDto.setTheatreId(1L);
        requestDto.setNumberOfShowsPerDay(2);
        requestDto.setShowTimings(List.of(LocalTime.of(10, 0), LocalTime.of(18, 0)));



        scheduleResponseDto = MovieScheduleResponseDto.builder()
                .id(100L)
                .movieTitle("Test Movie")
                .theatreName("Test Theatre")
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();

        theatre = Theatre.builder()
                .id(1L)
                .name("Test Theatre")
                .seats(List.of(new Seat(), new Seat(), new Seat()))
                .build();

        movieSchedule = MovieSchedule.builder().id(100L).theatre(theatre).build();
    }

    @Test
    void createScheduleWithShows_shouldCreateScheduleAndGenerateShows() {
        // Arrange
        when(movieScheduleService.createMovieSchedule(requestDto)).thenReturn(scheduleResponseDto);
        when(movieScheduleService.getScheduleById(100L)).thenReturn(movieSchedule);
        when(theatreService.getTheatreEntityById(1L)).thenReturn(theatre);
        when(responseMapper.convertTResponseDto(any(MovieSchedule.class))).thenReturn(scheduleResponseDto);

        // Act
        MovieScheduleResponseDto result = orchestrationService.createScheduleWithShows(requestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(100L);

        // Verify createMovieShow called correct number of times
        // 3 days * 2 shows per day = 6 total shows
        verify(movieShowService, times(6)).createMovieShow(any(MovieShow.class), eq(theatre));

        // Capture one of the calls to verify details
        ArgumentCaptor<MovieShow> showCaptor = ArgumentCaptor.forClass(MovieShow.class);
        verify(movieShowService, atLeastOnce()).createMovieShow(showCaptor.capture(), eq(theatre));

        MovieShow capturedShow = showCaptor.getValue();
        assertThat(capturedShow.getMovieSchedule()).isEqualTo(movieSchedule);
        assertThat(capturedShow.getAvailableSeatsCount()).isEqualTo(3);
    }
}
