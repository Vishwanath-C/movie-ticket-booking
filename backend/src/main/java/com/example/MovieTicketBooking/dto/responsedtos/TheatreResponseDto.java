package com.example.MovieTicketBooking.dto.responsedtos;

import com.example.MovieTicketBooking.dto.SeatDto;
import lombok.Builder;

import java.util.List;


@Builder
public record TheatreResponseDto(
        Long id,
        String name,
        String location,
        List<SeatDto> seats)
{
}
