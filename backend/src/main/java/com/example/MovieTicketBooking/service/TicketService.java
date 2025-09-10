package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.TicketRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TicketResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.TicketDTOMapper;
import com.example.MovieTicketBooking.model.*;
import com.example.MovieTicketBooking.repository.MovieShowRepository;
import com.example.MovieTicketBooking.repository.ShowSeatRepository;
import com.example.MovieTicketBooking.repository.TicketRepository;
import com.example.MovieTicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService
{
    private final  TicketRepository ticketRepository;
    private final  UserService userService;
    private final TicketSeatService ticketSeatService;
    private final  MovieShowService movieShowService;
    private final  MovieShowRepository movieShowRepository;
    private final  TicketDTOMapper ticketDTOMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    /**
     * Books a ticket for the user and generates a response with the ticket details.
     *
     * @param ticketRequestDto the ticket request data
     * @return the response with the ticket details
     */
    @Transactional
    public TicketResponseDto bookTicket(TicketRequestDto ticketRequestDto) {
        MovieShow movieShow = movieShowService.getMovieShowById(ticketRequestDto.getMovieShowId());

        User user = userRepository.findByEmail(ticketRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<ShowSeat> seatsToBook = showSeatRepository.findAllById(ticketRequestDto.getShowSeatIds());

        for (ShowSeat seat : seatsToBook) {
            if (seat.isBooked()) {
                throw new IllegalStateException("Seat " + seat.getSeat().getSeatNumber() + " already booked");
            }
        }

        seatsToBook.forEach(showSeat -> showSeat.setBooked(true));

        showSeatRepository.saveAll(seatsToBook);

        BigDecimal totalPrice = seatsToBook.stream()
                .map(ShowSeat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovieShow(movieShow);
        ticket.setTicketSeats(new ArrayList<>());
        ticket.setTotalPrice(totalPrice);

        for (ShowSeat seat : seatsToBook) {
            TicketSeat ticketSeat = new TicketSeat();
            ticketSeat.setTicket(ticket);
            ticketSeat.setShowSeat(seat);
            ticket.getTicketSeats().add(ticketSeat);
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        List<String> seatNumbers = seatsToBook.stream()
                .map(showSeat -> showSeat.getSeat().getSeatNumber()).collect(Collectors.toList());

        movieShow.setAvailableSeatsCount(movieShow.getAvailableSeatsCount() - seatNumbers.size());
        movieShowRepository.save(movieShow);

        return TicketResponseDto.builder()
                .ticketId(savedTicket.getId())
                .movieTitle(ticket.getMovieShow().getMovieSchedule().getMovie().getTitle())
                .theatreName(ticket.getMovieShow().getMovieSchedule().getTheatre().getName())
                .showDate(ticket.getMovieShow().getShowDate())
                .showTime(ticket.getMovieShow().getShowTime())
                .seatNumbers(seatNumbers)
                .totalPrice(ticket.getTotalPrice())
                .build();
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id the ticket ID
     * @return the ticket response dto
     */
    public TicketResponseDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return TicketResponseDto.builder()
                .ticketId(ticket.getId())
                .showDate(ticket.getMovieShow().getShowDate())
                .showTime(ticket.getMovieShow().getShowTime())
                .movieTitle(ticket.getMovieShow().getMovieSchedule().getMovie().getTitle())
                .theatreName(ticket.getMovieShow().getMovieSchedule().getTheatre().getName())
                .seatNumbers(ticket.getTicketSeats().stream()
                        .map(ticketSeat -> ticketSeat.getShowSeat().getSeat().getSeatNumber())
                        .toList())
                .totalPrice(ticket.getTotalPrice())
                .build();
    }

    /**
     * Retrieves all tickets
     *
     * @return a list of all tcickets
     */
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Retrievs list of tickets for a specific user
     *
     * @param id user id
     * @return list of tickets response dto
     */
    public List<TicketResponseDto> getTicketsByUserId(Long id) {
        User user = userService.getUserById(id);
        List<Ticket> tickets = ticketRepository.findAllByUser(user);
        List<TicketResponseDto> responseDtos = new ArrayList<>();


        for (Ticket ticket : tickets) {
            TicketResponseDto responseDto = ticketDTOMapper.toResponseDto(ticket);

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    /**
     * Retrives upcoming tickets for a specific user
     *
     * @param id the user id
     * @return list of upcoming ticket response dtos
     */
    public List<TicketResponseDto> getUpcomingTicketsByUserId(Long id) {
        User user = userService.getUserById(id);
        List<Ticket> tickets = ticketRepository.findAllByUser(user);

        List<TicketResponseDto> responseDtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Ticket ticket : tickets) {
            LocalDateTime showDateTime = LocalDateTime.of(
                    ticket.getMovieShow().getShowDate(),
                    ticket.getMovieShow().getShowTime()
            );

            if (showDateTime.isBefore(now))
                continue;


            List<String> seatNumbers = ticket.getTicketSeats().stream()
                    .map(ts -> ts.getShowSeat().getSeat().getSeatNumber())
                    .toList();

            TicketResponseDto responseDto = ticketDTOMapper.toResponseDto(ticket);

            responseDtos.add(responseDto);

        }

        return responseDtos;
    }

    /**
     * Retrieves finished tickets for a specific user
     *
     * @param id the user id
     * @return list of finished ticket response dtos
     */
    public List<TicketResponseDto> getFinishedTicketsByUserId(Long id) {
        User user = userService.getUserById(id);
        List<Ticket> tickets = ticketRepository.findAllByUser(user);
        List<TicketResponseDto> responseDtos = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Ticket ticket : tickets) {
            LocalDateTime showDateTime = LocalDateTime.of(ticket.getMovieShow().getShowDate(), ticket.getMovieShow().getShowTime());

            if (!showDateTime.isBefore(now))
                continue;

            List<String> seatNumbers = ticket.getTicketSeats().stream()
                    .map(ts -> ts.getShowSeat().getSeat().getSeatNumber())
                    .toList();

            TicketResponseDto responseDto = ticketDTOMapper.toResponseDto(ticket);


            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
