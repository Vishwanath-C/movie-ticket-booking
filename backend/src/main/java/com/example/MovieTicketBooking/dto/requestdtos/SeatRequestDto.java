package com.example.MovieTicketBooking.dto.requestdtos;

import com.example.MovieTicketBooking.model.enums.SeatType;

import java.math.BigDecimal;

public record SeatRequestDto
        (
                String seatNumber,
                SeatType seatType,
                BigDecimal price
        )
{
}
