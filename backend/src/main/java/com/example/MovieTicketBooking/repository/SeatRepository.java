package com.example.MovieTicketBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findByTheatre(Theatre theatre);
}
