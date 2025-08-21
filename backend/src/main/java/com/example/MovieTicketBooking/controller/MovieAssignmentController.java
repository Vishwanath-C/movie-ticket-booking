package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.MovieAssignmentRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieAssignmentResponseDto;
import com.example.MovieTicketBooking.service.MovieAssignmentService;
import com.example.MovieTicketBooking.service.MovieSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/movie-assignments")
public class MovieAssignmentController {

    @Autowired
    MovieAssignmentService movieAssignmentService;

    @Autowired
    MovieSharedService movieSharedService;

    @PostMapping("/create-assignment")
    public ResponseEntity<MovieAssignmentResponseDto> createAssignment(@RequestBody MovieAssignmentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSharedService.createMovieShows(dto));
    }

}
