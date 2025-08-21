package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.mapper.TicketSeatDtoMapper;
import com.example.MovieTicketBooking.model.TicketSeat;
import com.example.MovieTicketBooking.repository.TicketSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketSeatService {
    
    @Autowired
    TicketSeatRepository ticketSeatRepository;

    @Autowired
    TicketSeatDtoMapper ticketSeatDtoMapper;

    @Transactional
    public List<TicketSeat> saveAll(List<TicketSeat> seats) {
        return ticketSeatRepository.saveAll(seats);
    }

    public TicketSeat createTicketSeat(TicketSeat ticketSeat){
        return ticketSeatRepository.save(ticketSeat);
    }
}

