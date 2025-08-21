package com.example.MovieTicketBooking.dto.responsedtos;

import com.example.MovieTicketBooking.dto.requestdtos.SeatRequestDto;

import java.util.List;

public record TheatreResponseDto(
        Long id,
        String name,
        String location,
        List<SeatRequestDto> seats)
{
}
