package com.example.MovieTicketBooking.dto.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatRequestDto
{
    private Long id;
    private Long movieShowId;
    private Long seatId;
    private BigDecimal price;
    private boolean booked;
    private String seatNumber;
}
