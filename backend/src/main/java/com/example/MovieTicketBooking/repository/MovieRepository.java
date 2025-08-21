package com.example.MovieTicketBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
