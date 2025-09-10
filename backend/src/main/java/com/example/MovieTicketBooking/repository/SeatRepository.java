package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findAllByTheatre(Theatre theatre);
}
