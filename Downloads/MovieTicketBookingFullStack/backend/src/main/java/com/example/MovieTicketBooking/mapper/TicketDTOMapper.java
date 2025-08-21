package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.TicketDto;
import com.example.MovieTicketBooking.model.Ticket;
import com.example.MovieTicketBooking.repository.UserRepository;
import com.example.MovieTicketBooking.service.MovieShowService;
import com.example.MovieTicketBooking.service.TicketSeatService;
import com.example.MovieTicketBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketDTOMapper {

    @Autowired
    UserService userService;

    @Autowired
    ShowSeatRequestDtoMapper showSeatRequestDtoMapper;

    @Autowired
    MovieShowService movieShowService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketSeatService ticketSeatService;

    public Ticket mapToTicket(TicketDto ticketDto){
        Ticket ticket = new Ticket();
        ticket.setMovieShow(movieShowService.getMovieShowById(ticketDto.getMovieShowId()));
        ticket.setUser(userRepository.findByEmail(ticketDto.getEmail()));
        ticket.setTotalPrice(ticketDto.getTotalPrice());
        return ticket;
    }

    public TicketDto toDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setMovieShowId(ticket.getMovieShow().getId());
        ticketDto.setEmail(ticket.getUser().getEmail());
//        ticketDto.setShowSeatRequestDtos();
        return ticketDto;
    }
    
}
