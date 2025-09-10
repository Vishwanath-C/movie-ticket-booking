package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.MovieRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController
{
    private final MovieService movieService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequestDto movieRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieRequestDto));
    }

    @GetMapping("")
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/with-shows")
    public ResponseEntity<List<MovieResponseDto>> getMoviesWithShows() {
        return ResponseEntity.ok(movieService.getMoviesWithShows());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieResponseDto>> getUpcomingMovies() {
        return ResponseEntity.ok(movieService.getUpcomingMovies());
    }

    @GetMapping("/now-running")
    public ResponseEntity<List<MovieResponseDto>> getCurrentlyRunningMovies() {
        return ResponseEntity.ok(movieService.getCurrentShowingMovies());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(movieService.getByTitle(title));
    }
}
