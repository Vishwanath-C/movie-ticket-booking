package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.service.TicketSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/ticketseats")
public class TicketSeatController {

    @Autowired
    TicketSeatService ticketSeatService;

    // @Autowired
    // TicketSeatDto ticketSeatDto;

    // @PostMapping("/create-ticket-seat")
    // public ResponseEntity<TicketSeat> postMethodName(@RequestBody TicketSeatDto ticketSeatDto) {

    //     return ResponseEntity.status(HttpStatus.CREATED).body(ticketSeatService.createTicketSeat(ticketSeatDto));
    // }

}
