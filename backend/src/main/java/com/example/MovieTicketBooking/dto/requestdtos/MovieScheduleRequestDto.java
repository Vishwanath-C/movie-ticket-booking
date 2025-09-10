package com.example.MovieTicketBooking.dto.requestdtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieScheduleRequestDto
{

    @NotNull(message = "Movie id is required")
    private Long movieId;

    @NotNull(message = "Theatre id is required")
    private Long theatreId;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be tomorrow or later")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be tomorrow or later")
    private LocalDate endDate;

    @NotEmpty(message = "Show times are required")
    private List<LocalTime> showTimings;

    @Min(value = 1, message = "Number of shows per day must be at least 1")
    private int numberOfShowsPerDay;
  }
