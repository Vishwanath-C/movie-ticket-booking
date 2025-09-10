package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.model.enums.Role;
import com.example.MovieTicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            // Create admin
            User admin = User.builder()
                    .firstName("System")
                    .lastName("Admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created!");
        }
    }
}
