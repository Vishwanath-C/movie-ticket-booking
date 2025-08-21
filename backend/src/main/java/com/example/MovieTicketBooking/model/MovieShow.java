package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "movie_show",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"movie_assignment_id", "show_date", "show_time"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieShow
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_assignment_id", nullable = false)
    private MovieAssignment movieAssignment;

    private LocalDate showDate;
    private LocalTime showTime;

    @OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowSeat> showSeats;

    private int availableSeatsCount;

    @Override
    public String toString() {
        return "MovieShow{" +
                "id=" + id +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", availableSeatsCount=" + availableSeatsCount +
                '}';
    }
}
