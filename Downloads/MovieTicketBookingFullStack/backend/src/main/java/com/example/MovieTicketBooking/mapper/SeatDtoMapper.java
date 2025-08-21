package com.example.MovieTicketBooking.mapper;

import org.springframework.stereotype.Component;

import com.example.MovieTicketBooking.dto.SeatDto;
import com.example.MovieTicketBooking.model.Seat;

@Component
public class SeatDtoMapper {
    public Seat dtoToSeat(SeatDto seatDto){
        Seat seat = new Seat();
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setSeatType(seatDto.getSeatType());
        seat.setPrice(seatDto.getPrice());
        return seat;
    }
}
