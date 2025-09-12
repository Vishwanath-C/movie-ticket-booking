package com.example.MovieTicketBooking.model;

import com.example.MovieTicketBooking.model.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "seat", uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "theatre_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Seat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "seat_number", nullable = false)
    @ToString.Include
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @ToString.Include
    private SeatType seatType;

    @ToString.Include
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;




}
