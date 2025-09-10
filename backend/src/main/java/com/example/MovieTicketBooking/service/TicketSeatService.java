package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.mapper.TicketSeatDtoMapper;
import com.example.MovieTicketBooking.model.TicketSeat;
import com.example.MovieTicketBooking.repository.TicketSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketSeatService
{
    private final TicketSeatRepository ticketSeatRepository;
    private final TicketSeatDtoMapper ticketSeatDtoMapper;

    @Transactional
    public List<TicketSeat> saveAll(List<TicketSeat> seats) {
        return ticketSeatRepository.saveAll(seats);
    }

    public TicketSeat createTicketSeat(TicketSeat ticketSeat) {
        return ticketSeatRepository.save(ticketSeat);
    }
}

