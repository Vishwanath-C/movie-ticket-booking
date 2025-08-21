package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.controller.exchanges.request.LoginRequest;
import com.example.MovieTicketBooking.controller.exchanges.request.RegisterRequest;
import com.example.MovieTicketBooking.controller.exchanges.response.AuthResponse;
import com.example.MovieTicketBooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"}  )
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("Inside register");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Inside login controller modified never");
        System.out.println(request);
        return ResponseEntity.ok(authService.login(request));
    }

}
