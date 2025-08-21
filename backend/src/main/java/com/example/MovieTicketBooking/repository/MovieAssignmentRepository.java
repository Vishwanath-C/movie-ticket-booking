package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieAssignmentRepository extends JpaRepository<MovieAssignment, Long>{
    List<MovieAssignment> findMovieAssignmentsByMovie(Movie movie);
}
