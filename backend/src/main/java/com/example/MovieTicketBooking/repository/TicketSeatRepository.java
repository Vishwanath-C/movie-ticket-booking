package com.example.MovieTicketBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.TicketSeat;

@Repository
public interface TicketSeatRepository extends JpaRepository<TicketSeat, Long>{
    
}
