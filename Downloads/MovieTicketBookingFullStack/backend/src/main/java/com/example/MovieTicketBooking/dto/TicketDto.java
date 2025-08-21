package com.example.MovieTicketBooking.dto;

import com.example.MovieTicketBooking.dto.requestdtos.ShowSeatRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private List<ShowSeatRequestDto> showSeatRequestDtos;
    private String email;
    private Long movieShowId;
    private BigDecimal totalPrice;

}
