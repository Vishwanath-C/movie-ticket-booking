package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.TicketDto;
import com.example.MovieTicketBooking.dto.responsedtos.TicketResponseDto;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.repository.UserRepository;
import com.example.MovieTicketBooking.service.TicketService;
import com.example.MovieTicketBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    TicketService ticketService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketDto ticketDto) {
        System.out.println("Inside ticket controller");
        System.out.println(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDto));
    }

    @GetMapping("/get-tickets")
    public ResponseEntity<List<TicketResponseDto>> getTicketsByUser(@AuthenticationPrincipal Jwt jwt){
        String email = jwt.getSubject();
        System.out.println("EMAIL : " + email);
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        Long userId = user.getId();
        List<TicketResponseDto> responseDtos = ticketService.getTicketsByUserId(userId);
        System.out.println(responseDtos);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }
    
}
