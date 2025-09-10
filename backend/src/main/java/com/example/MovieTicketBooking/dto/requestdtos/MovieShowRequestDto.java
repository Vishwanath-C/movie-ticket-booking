package com.example.MovieTicketBooking.dto.requestdtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowRequestDto {

    @NotNull(message = "Showtime is required")
    private LocalTime showTime;

    @NotNull(message = "Movie schedule id is required")
    private Long movieScheduleId;

    @NotNull(message = "Number of shows per day is required")
    private int numberOfShowsPerDay;
}
