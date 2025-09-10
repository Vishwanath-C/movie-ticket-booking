package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "show_seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_show_id", "seat_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    // Each ShowSeat belongs to a specific MovieShow
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_show_id", nullable = false)
    @NotNull(message = "MovieShow is required")
    private MovieShow movieShow;

    // The physical seat that this ShowSeat represents
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    @NotNull(message = "Seat is required")
    private Seat seat;

    // Price of this seat for the show
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private BigDecimal price;

    // Indicates whether the seat has been booked
    @Builder.Default
    private boolean booked = false;
}
