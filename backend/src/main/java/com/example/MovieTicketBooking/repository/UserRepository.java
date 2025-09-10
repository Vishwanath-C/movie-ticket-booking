package com.example.MovieTicketBooking.repository;

import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
}
