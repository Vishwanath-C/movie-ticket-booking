package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long>{
    List<MovieSchedule> findAllByMovie(Movie movie);
    List<MovieSchedule> findAllByTheatre(Theatre theatre);


}
