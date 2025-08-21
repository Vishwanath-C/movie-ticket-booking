package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.MovieDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/create-movie")
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieDto));
    }

    @GetMapping("/all-movies")
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(movieService.getByTitle(title));
    }
}
