package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long>
{
}
