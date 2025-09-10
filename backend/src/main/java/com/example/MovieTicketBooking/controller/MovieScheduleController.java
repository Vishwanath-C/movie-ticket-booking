package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.service.MovieScheduleService;
import com.example.MovieTicketBooking.service.MovieSharedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class MovieScheduleController
{
    private final MovieScheduleService movieScheduleService;
    private final MovieSharedService movieSharedService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieScheduleResponseDto> createSchedule(@Valid @RequestBody MovieScheduleRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSharedService.createMovieShows(dto));
    }

    @GetMapping("/theatres/{theatreId}")
    public ResponseEntity<MovieScheduleResponseDto> getMovieScheduleByTheatre(@Validated @PathVariable Long theatreId) {
        return ResponseEntity.ok(movieScheduleService.getMovieSchedulesByTheatre(theatreId));
    }

}
