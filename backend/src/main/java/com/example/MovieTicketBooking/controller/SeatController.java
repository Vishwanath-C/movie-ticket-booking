package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.enums.SeatType;
import com.example.MovieTicketBooking.service.SeatService;
import com.example.MovieTicketBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController
{
    private final SeatService seatService;
    private final TheatreService theatreService;

    @GetMapping("/theatre/{theatre-id}/seats")
    public List<Seat> getSeatsByTheatre(@PathVariable Long theatreId) {
        return seatService.getAllSeatsByTheatreId(theatreId);
    }

    @GetMapping("/types")
    public ResponseEntity<SeatType[]> getSeatTypes() {
        return ResponseEntity.ok().body(SeatType.values());
    }


}
