package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.TicketSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSeatRepository extends JpaRepository<TicketSeat, Long>{


    
}
