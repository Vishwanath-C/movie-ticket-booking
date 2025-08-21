package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_seat",
    uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id", "movie_show_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSeat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private ShowSeat showSeat;

    @ManyToOne
    @JoinColumn(name = "movie_show_id", nullable = false)
    private MovieShow movieShow;

    @ManyToOne
    private Ticket ticket;
}
