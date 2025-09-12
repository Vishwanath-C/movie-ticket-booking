package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.TicketRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TicketResponseDto;
import com.example.MovieTicketBooking.mapper.TicketDTOMapper;
import com.example.MovieTicketBooking.model.*;
import com.example.MovieTicketBooking.repository.MovieShowRepository;
import com.example.MovieTicketBooking.repository.ShowSeatRepository;
import com.example.MovieTicketBooking.repository.TicketRepository;
import com.example.MovieTicketBooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TicketServiceTest
{
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @Mock
    private MovieShowService movieShowService;

    @Mock
    private MovieShowRepository movieShowRepository;

    @Mock
    private TicketDTOMapper ticketDTOMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @InjectMocks
    private TicketService ticketService;

    private TicketRequestDto ticketRequestDto;
    private MovieShow movieShow;
    private User user;
    private ShowSeat seat1, seat2;
    private Ticket savedTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticketRequestDto = TicketRequestDto.builder()
                .movieShowId(1L)
                .email("text@example.com")
                .showSeatIds(List.of(100L, 101L))
                .build();

        user = User.builder()
                .id(1L)
                .email("text@example.com")
                .build();

        Movie movie = Movie.builder().id(1L).title("KGF").build();
        Theatre theatre = Theatre.builder().id(1L).name("PVR").build();

        MovieSchedule schedule = MovieSchedule.builder().movie(movie).theatre(theatre).build();

        movieShow = MovieShow.builder()
                .id(1L)
                .showDate(LocalDate.of(2025, 9, 14))
                .showTime(LocalTime.of(14, 0))
                .availableSeatsCount(10)
                .movieSchedule(schedule)
                .build();

        seat1 = ShowSeat.builder().id(100L).seat(Seat.builder().seatNumber("A1").build()).price(BigDecimal.valueOf(200)).booked(false).build();
        seat2 = ShowSeat.builder().id(101L).seat(Seat.builder().seatNumber("A2").build()).price(BigDecimal.valueOf(250)).booked(false).build();

        savedTicket = Ticket.builder()
                .movieShow(movieShow)
                .user(user)
                .ticketSeats(new ArrayList<>())
                .totalPrice(BigDecimal.valueOf(450))
                .build();
    }

    @Test
    void bookTicket_shouldBookSeatsAndReturnResponse(){
        when(movieShowService.getMovieShowById(1L)).thenReturn(movieShow);
        when(userRepository.findByEmail("text@example.com")).thenReturn(Optional.of(user));
        when(showSeatRepository.findAllById(ticketRequestDto.getShowSeatIds())).thenReturn(List.of(seat1, seat2));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        TicketResponseDto responseDto = ticketService.bookTicket(ticketRequestDto);

        assertNotNull(responseDto);
//        assertEquals(responseDto.getTicketId(), 1L);
        assertEquals(responseDto.getSeatNumbers(), List.of("A1", "A2"));
        assertEquals(responseDto.getTotalPrice(), BigDecimal.valueOf(450));

        assertTrue(seat1.isBooked());
        assertTrue(seat2.isBooked());

        verify(showSeatRepository).saveAll(List.of(seat1, seat2));
        verify(movieShowRepository).save(movieShow);
        verify(ticketRepository).save(any(Ticket.class));

    }
}
