package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_show",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"movie_schedule_id", "show_date", "show_time"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MovieShow
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id", nullable = false)
    private MovieSchedule movieSchedule;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    @OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ShowSeat> showSeats = new ArrayList<>();

    @Column(nullable = false)
    @Min(value = 0, message = "Seat count should be more than one")
    private int availableSeatsCount;

}
