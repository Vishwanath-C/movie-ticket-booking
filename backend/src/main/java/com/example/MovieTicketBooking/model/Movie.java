package com.example.MovieTicketBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String description;
    private int duration;

    @OneToMany(mappedBy = "movie")
    private List<MovieAssignment> movieAssignments = new ArrayList<>();

    @Override
    public String toString(){
        return "Movie{" +
        "id=" + id +
        ", title='" + title + '\'' +
        '}';
    }
}
