package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.responsedtos.MovieShowResponseDto;
import com.example.MovieTicketBooking.service.MovieService;
import com.example.MovieTicketBooking.service.MovieShowService;
import com.example.MovieTicketBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/movieshows")
public class MovieShowController
{

    private final MovieShowService movieShowService;
    private final MovieService movieService;

    @Autowired
    TheatreService theatreService;

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<List<List<MovieShowResponseDto>>> getMovieShowsByMovie(@PathVariable Long movieId) {
        List<List<MovieShowResponseDto>> movieShows = movieShowService.getMovieShowsByMovieId(movieId);
        return ResponseEntity.ok().body(movieShows);
    }

    @GetMapping("/movies/{movieId}/date/{date}")
    public ResponseEntity<List<List<MovieShowResponseDto>>> getMovieShowsByMovieByDate(@PathVariable Long movieId,
                                                                                       @PathVariable LocalDate date) {
        List<List<MovieShowResponseDto>> movieShows = movieShowService.getMovieShowsByMovieIdByDate(movieId, date);
        return ResponseEntity.ok().body(movieShows);
    }

    @GetMapping("/movies/{movieId}/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @PathVariable Long movieId, @RequestParam(defaultValue = "10") int days
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(movieShowService.getAvailableDates(movieId, days));
    }
}
