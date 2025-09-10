package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.Ticket;
import com.example.MovieTicketBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    List<Ticket> findAllByUser(User user);
}
