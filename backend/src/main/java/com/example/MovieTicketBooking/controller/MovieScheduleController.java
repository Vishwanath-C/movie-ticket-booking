package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.service.MovieScheduleOrchestrationService;
import com.example.MovieTicketBooking.service.MovieScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class MovieScheduleController
{
    private final MovieScheduleService movieScheduleService;
    private final MovieScheduleOrchestrationService movieScheduleOrchestrationService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieScheduleResponseDto> createSchedule(@Valid @RequestBody MovieScheduleRequestDto dto) {
        System.out.println("Inside");
        return ResponseEntity.status(HttpStatus.CREATED).body(movieScheduleOrchestrationService.createScheduleWithShows(dto));
    }

    @GetMapping("/theatres/{theatreId}")
    public ResponseEntity<MovieScheduleResponseDto> getMovieScheduleByTheatre(@Validated @PathVariable Long theatreId) {
        return ResponseEntity.ok(movieScheduleService.getMovieSchedulesByTheatre(theatreId));
    }

}
