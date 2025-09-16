package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.model.TicketSeat;
import com.example.MovieTicketBooking.repository.TicketSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TicketSeatServiceTest
{
    @Mock
    private  TicketSeatRepository ticketSeatRepository;

    @InjectMocks
    private TicketSeatService ticketSeatService;

    private TicketSeat seat1, seat2;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

      seat1 = new TicketSeat();
      seat1.setId(1L);

      seat2 = new TicketSeat();
      seat2.setId(2L);

    }

    @Test
    void saveAll_shouldSaveListOfTicketSeats(){
        List<TicketSeat> seats = List.of(seat1, seat2);

        when(ticketSeatRepository.saveAll(seats)).thenReturn(seats);

        List<TicketSeat> savedSeats = ticketSeatService.saveAll(seats);
        assertEquals(savedSeats.size(), 2);
    }
}
