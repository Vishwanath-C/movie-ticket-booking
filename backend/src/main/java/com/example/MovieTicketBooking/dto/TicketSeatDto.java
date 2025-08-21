package com.example.MovieTicketBooking.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSeatDto {
    private List<Seat> seats;
    private MovieShow movieShow;
    private User user;
    private BigDecimal totalPrice;
}
