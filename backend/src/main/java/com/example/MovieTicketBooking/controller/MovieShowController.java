package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.responsedtos.MovieShowResponseDto;
import com.example.MovieTicketBooking.service.MovieService;
import com.example.MovieTicketBooking.service.MovieShowService;
import com.example.MovieTicketBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/{movieId}/shows")
public class MovieShowController
{
    private final MovieShowService movieShowService;
    private final MovieService movieService;
    private final TheatreService theatreService;

    @GetMapping("")
    public ResponseEntity<List<List<MovieShowResponseDto>>> getMovieShowsByMovie(@PathVariable Long movieId) {
        List<List<MovieShowResponseDto>> movieShows = movieShowService.getMovieShowsByMovieId(movieId);
        return ResponseEntity.ok().body(movieShows);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<List<MovieShowResponseDto>>> getMovieShowsByMovieByDate(@PathVariable Long movieId,
                                                                                       @PathVariable LocalDate date) {
        List<List<MovieShowResponseDto>> movieShows = movieShowService.getMovieShowsByMovieIdByDate(movieId, date);
        return ResponseEntity.ok().body(movieShows);
    }

    @GetMapping("/available-dates?days=10")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @PathVariable Long movieId, @RequestParam(defaultValue = "10") int days
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(movieShowService.getAvailableDates(movieId, days));
    }
}
