package com.example.MovieTicketBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.Ticket;
import com.example.MovieTicketBooking.model.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    List<Ticket> findByUser(User user);
}
