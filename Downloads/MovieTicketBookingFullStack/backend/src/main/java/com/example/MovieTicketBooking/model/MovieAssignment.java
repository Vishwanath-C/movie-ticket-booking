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
@Table(name = "movie_assignment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "theatre_theatre_id"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAssignment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theatre theatre;

    @OneToMany(mappedBy = "movieAssignment")
    private List<MovieShow> movieShows;

    private List<LocalTime> showTimings;

    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public String toString() {
        return "MovieAssignment{" +
                "id=" + id +
                ", movie=" + movie +
                ", theatre=" + theatre +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
