package com.example.MovieTicketBooking.dto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto
{
    private Long ticket_id;
    private String movie_name;
    private String theatre_name;
    private LocalDate showDate;
    private LocalTime showTime;
    private List<String> seatNumbers;
    private BigDecimal totalPrice;
}
