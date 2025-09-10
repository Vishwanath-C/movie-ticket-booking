package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.LoginRequest;
import com.example.MovieTicketBooking.dto.requestdtos.RegisterRequest;
import com.example.MovieTicketBooking.dto.responsedtos.AuthResponse;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.model.enums.Role;
import com.example.MovieTicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     * This method creates a new user, encodes their password, and returns a JWT token.
     */
    public AuthResponse register(RegisterRequest request) {

        // checks if the email is already in user
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create the user with the ROLE_USER by default
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // Generate JWT for the user
        String jwToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(jwToken).build();
    }

    /**
     * Login an existing user.
     * This method checks the credentials, authenticates the user, and returns a JWT token.
     */
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid credentials", ex);
        }

        UserDetails user = userService.loadUserByUsername(request.getEmail());
        String jwToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(jwToken).build();
    }

    public AuthResponse registerAdmin(RegisterRequest request) {
        User admin = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
        String token = jwtService.generateToken(admin);
        return AuthResponse.builder().accessToken(token).build();
    }
}
