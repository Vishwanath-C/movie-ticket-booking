package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.requestdtos.ShowSeatRequestDto;
import com.example.MovieTicketBooking.model.ShowSeat;
import com.example.MovieTicketBooking.service.MovieShowService;
import com.example.MovieTicketBooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatRequestDtoMapper
{
    @Autowired
    SeatService seatService;

    @Autowired
    MovieShowService movieShowService;
    public ShowSeat convertToShowSeat(ShowSeatRequestDto requestDto){
        return ShowSeat.builder()
                .id(requestDto.getSeatId())
                .seat(seatService.getSeatById(requestDto.getSeatId()))
                .movieShow(movieShowService.getMovieShowById(requestDto.getMovieShowId()))
                .price(requestDto.getPrice())
                .booked(requestDto.isBooked())
                .build();
    }
}
