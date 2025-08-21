package com.example.MovieTicketBooking.dto.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieAssignmentRequestDto {
    private Long movieId;
    private Long theatreId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalTime> showTimings;
    private int numberOfShowsPerDay;
  }
