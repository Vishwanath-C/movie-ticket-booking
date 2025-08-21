package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.repository.TicketSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketSeatDtoMapper {
    @Autowired
    TicketSeatRepository ticketSeatRepository;
    

}
