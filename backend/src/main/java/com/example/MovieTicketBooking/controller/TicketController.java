package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.TicketRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TicketResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.repository.UserRepository;
import com.example.MovieTicketBooking.service.TicketService;
import com.example.MovieTicketBooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController
{
    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.bookTicket(ticketRequestDto));
    }

    @GetMapping("")
    public ResponseEntity<List<TicketResponseDto>> getTicketsByUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Long userId = userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<TicketResponseDto> responseDtos = ticketService.getTicketsByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("upcoming")
    public ResponseEntity<List<TicketResponseDto>> getUpcomingTicketsByUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();

        Long userId = userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<TicketResponseDto> responseDtos = ticketService.getUpcomingTicketsByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("finished")
    public ResponseEntity<List<TicketResponseDto>> getFinishedTicketsByUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Long userId = userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<TicketResponseDto> responseDtos = ticketService.getFinishedTicketsByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

}
