package com.example.MovieTicketBooking.dto.responsedtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAssignmentResponseDto {
    private Long id;
    private String movie;
    private String theatre;
    private LocalDate startDate;
    private LocalDate endDate;
}
