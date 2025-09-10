package com.example.MovieTicketBooking.dto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieScheduleResponseDto
{
    private Long id;
    private String movieTitle;
    private String theatreName;
    private LocalDate startDate;
    private LocalDate endDate;
}
