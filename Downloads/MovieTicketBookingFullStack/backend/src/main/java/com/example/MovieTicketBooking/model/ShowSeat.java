package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "show_seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MovieShow movieShow;

    @ManyToOne
    private Seat seat;

    private BigDecimal price;
    private boolean booked;
}
