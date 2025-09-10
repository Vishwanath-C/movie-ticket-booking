package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "movie_schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "theatre_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MovieSchedule
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @NotNull
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    @NotNull
    private Theatre theatre;

    @OneToMany(mappedBy = "movieSchedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieShow> movieShows;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "movie_schedule_show_timings", joinColumns = @JoinColumn(name = "movie_schedule_id"))
    private List<LocalTime> showTimings;




}
