package com.example.MovieTicketBooking.dto;

import java.math.BigDecimal;

import com.example.MovieTicketBooking.model.enums.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private String seatNumber;
    private SeatType seatType;
    private BigDecimal price;
}
