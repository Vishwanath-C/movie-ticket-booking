package com.example.MovieTicketBooking.repository;

import org.springframework.stereotype.Repository;

import com.example.MovieTicketBooking.model.Theatre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findOneByName(String name);
}
