package com.example.MovieTicketBooking.model;

import com.example.MovieTicketBooking.model.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "seat", uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "theatre_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @Override
    public String toString() {
        return "Seat [id=" + id + ", seatNumber=" + seatNumber + ", seatType=" + seatType + ", price=" + price
                + "]";
    }


}
