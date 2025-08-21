package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.TicketDto;
import com.example.MovieTicketBooking.dto.requestdtos.ShowSeatRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TicketResponseDto;
import com.example.MovieTicketBooking.mapper.ShowSeatRequestDtoMapper;
import com.example.MovieTicketBooking.mapper.TicketDTOMapper;
import com.example.MovieTicketBooking.model.*;
import com.example.MovieTicketBooking.repository.ShowSeatRepository;
import com.example.MovieTicketBooking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserService userService;

    @Autowired
    TicketSeatService ticketSeatService;

    @Autowired
    MovieShowService movieShowService;

    @Autowired
    TicketDTOMapper ticketDTOMapper;

    @Autowired
    ShowSeatRequestDtoMapper showSeatRequestDtoMapper;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Transactional
    public TicketResponseDto createTicket(TicketDto ticketDto) {
        Ticket ticket = ticketDTOMapper.mapToTicket(ticketDto);
        Ticket ticketCreated = ticketRepository.save(ticket);

        List<TicketSeat> ticketSeatList = new ArrayList<>();

        for (ShowSeatRequestDto seat : ticketDto.getShowSeatRequestDtos()) {
            ShowSeat showSeat = showSeatRepository.findById(seat.getId()).get();

            TicketSeat ticketSeat = new TicketSeat();
            ticketSeat.setShowSeat(showSeat);
            ticketSeat.setTicket(ticketCreated);
            ticketSeat.setMovieShow(movieShowService.getMovieShowById(seat.getMovieShowId()));
            ticketSeatList.add(ticketSeat);

            showSeat.setBooked(true);
        }

        int seatCountBefore = movieShowService.getMovieShowById(ticketDto.getMovieShowId())
                        .getAvailableSeatsCount();

        movieShowService.getMovieShowById(ticketDto.getMovieShowId())
                        .setAvailableSeatsCount(seatCountBefore - ticketDto.getShowSeatRequestDtos().size());

        ticketSeatService.saveAll(ticketSeatList);

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setTicket_id(ticket.getId());
        responseDto.setMovie_name(ticket.getMovieShow().getMovieAssignment().getMovie().getTitle());
        responseDto.setTotalPrice(ticket.getTotalPrice());
        responseDto.setShowDate(ticket.getMovieShow().getShowDate());
        responseDto.setShowTime(ticket.getMovieShow().getShowTime());

        List<String> list = new ArrayList<>();

        for(int i = 0; i < ticketDto.getShowSeatRequestDtos().size(); i++){
            list.add(ticketDto.getShowSeatRequestDtos().get(i).getSeatNumber());
        }

        responseDto.setSeatNumbers(list);

        System.out.println("RESPONSE DTO  : " + responseDto);

        return responseDto;
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).get();
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

//    public List<Ticket> getTicketsByUserId(Long id) {
//        User user = userService.getUserById(id);
//        return ticketRepository.findByUser(user);
//    }

    public List<TicketResponseDto> getTicketsByUserId(Long id) {
        User user = userService.getUserById(id);
        List<Ticket> tickets = ticketRepository.findByUser(user);
        List<TicketResponseDto> responseDtos = new ArrayList<>();

        for(Ticket ticket : tickets){
            TicketResponseDto responseDto = new TicketResponseDto();
            responseDto.setTicket_id(ticket.getId());
            responseDto.setMovie_name(ticket.getMovieShow().getMovieAssignment().getMovie().getTitle());
            responseDto.setTheatre_name(ticket.getMovieShow().getMovieAssignment().getTheatre().getName());
            responseDto.setTotalPrice(ticket.getTotalPrice());
            responseDto.setShowDate(ticket.getMovieShow().getShowDate());
            responseDto.setShowTime(ticket.getMovieShow().getShowTime());
            List<String> seatNumbers = new ArrayList<>();
            for(int i = 0; i < ticket.getTicketSeats().size(); i++){
                seatNumbers.add(ticket.getTicketSeats().get(i).getShowSeat().getSeat().getSeatNumber());
            }
            responseDto.setSeatNumbers(seatNumbers);

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
