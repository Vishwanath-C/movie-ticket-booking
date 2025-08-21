package com.example.MovieTicketBooking.dto.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowRequestDto {
    private LocalTime showTime;
    private Long movieAssignmentId;
    private int numberOfShowsPerDay;
    // private Long theatreId;
    // private Long movieId;
}
