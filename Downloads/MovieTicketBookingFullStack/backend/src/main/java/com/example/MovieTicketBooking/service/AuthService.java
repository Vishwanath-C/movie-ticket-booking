package com.example.MovieTicketBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MovieTicketBooking.controller.exchanges.request.LoginRequest;
import com.example.MovieTicketBooking.controller.exchanges.request.RegisterRequest;
import com.example.MovieTicketBooking.controller.exchanges.response.AuthResponse;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.model.enums.Role;
import com.example.MovieTicketBooking.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null)
            request.setRole(Role.USER);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        String jwToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(jwToken).build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail());
        String jwToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(jwToken).build();
    }
}
