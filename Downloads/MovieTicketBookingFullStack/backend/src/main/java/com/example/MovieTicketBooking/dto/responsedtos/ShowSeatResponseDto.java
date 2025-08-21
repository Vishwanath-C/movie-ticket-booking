package com.example.MovieTicketBooking.dto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatResponseDto
{
    private long id;
    private String seatNumber;
    private BigDecimal price;
    private boolean booked;
    private Long movieShowId;
    private Long seatId;
}
