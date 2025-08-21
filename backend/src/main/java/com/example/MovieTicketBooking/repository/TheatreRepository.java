package com.example.MovieTicketBooking.repository;

import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.Theatre;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre findByName(String name);
}
