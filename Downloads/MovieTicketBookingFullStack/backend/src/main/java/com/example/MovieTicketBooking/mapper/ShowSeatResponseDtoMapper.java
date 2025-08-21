package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.responsedtos.ShowSeatResponseDto;
import com.example.MovieTicketBooking.model.ShowSeat;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatResponseDtoMapper
{
    public ShowSeatResponseDto convertToDto(ShowSeat showSeat){
        return ShowSeatResponseDto.builder()
                .id(showSeat.getId())
                .price(showSeat.getPrice())
                .seatNumber(showSeat.getSeat().getSeatNumber())
                .booked(showSeat.isBooked())
                .movieShowId(showSeat.getMovieShow().getId())
                .seatId(showSeat.getSeat().getId())
                .build();
    }
}
