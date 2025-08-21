package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.MovieAssignment;
import com.example.MovieTicketBooking.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow, Long>
{
    List<MovieShow> findMovieShowsByMovieAssignment(MovieAssignment assignment);
}
