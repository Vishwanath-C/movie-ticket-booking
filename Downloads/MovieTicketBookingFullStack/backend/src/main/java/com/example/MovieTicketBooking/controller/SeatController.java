package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.SeatTypeRequest;
import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.model.enums.SeatType;
import com.example.MovieTicketBooking.service.SeatService;
import com.example.MovieTicketBooking.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    SeatService seatService;

    @Autowired
    TheatreService theatreService;

    @GetMapping("/theatre/{theatre-id}")
    public List<Seat> getSeatsByTheatre(@PathVariable Long theatreId) {
        return seatService.getAllSeatsByTheatreId(theatreId);
    }

    @PostMapping("/generate_seats/theatres/{theatreId}")
    public ResponseEntity<Void> createSeat(@RequestBody List<SeatTypeRequest> seatTypeRequests,
            @PathVariable Long theatreId) {
        Theatre theatre = theatreService.getTheatreById(theatreId);
        seatService.generateSeats(theatre, seatTypeRequests);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-seat-types")
    public ResponseEntity<SeatType[]> getSeatTypes() {
        System.out.println("Inside get seat types");
        return ResponseEntity.ok().body(SeatType.values());
    }
    

}
