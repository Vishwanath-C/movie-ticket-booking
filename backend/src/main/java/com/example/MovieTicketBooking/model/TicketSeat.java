package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_seat",
    uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id", "movie_show_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class TicketSeat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_seat_id", nullable = false)
    private ShowSeat showSeat;

    @ManyToOne
    private Ticket ticket;
}
