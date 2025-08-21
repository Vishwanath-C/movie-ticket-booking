package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.SeatTypeRequest;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.model.enums.SeatType;
import com.example.MovieTicketBooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    TheatreService theatreService;

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Seat with Id " + id + " not found!")));
    }

    public List<Seat> getAllSeatsByTheatreId(Long theatreId) {
        return seatRepository.findByTheatre(theatreService.getTheatreById(theatreId));
    }

    public void generateSeats(Theatre theatre, List<SeatTypeRequest> seatTypeRequests) {
        List<Seat> seats = new ArrayList<>();

        for (SeatTypeRequest seatTypeRequest : seatTypeRequests) {
            for (char row = 'A'; row < 'A' + seatTypeRequest.getRowCount(); row++) {
                for (int i = 1; i <= seatTypeRequest.getSeatsPerRow(); i++) {

                    String seatNumber = seatTypeRequest.getSeatType() == SeatType.GOLD ? ("G" + row + i)
                            : ("N" + row + i);

                    Seat seat = Seat.builder()
                            .seatType(seatTypeRequest.getSeatType())
                            .seatNumber(seatNumber)
                            .theatre(theatre)
                            .price(seatTypeRequest.getPrice())
                            .build();

                    seats.add(seat);
                }
            }
        }
        seatRepository.saveAll(seats);
    }
}
