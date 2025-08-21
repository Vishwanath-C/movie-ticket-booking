package com.example.MovieTicketBooking.dto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDto {
    private Long id;
    private String title;
    private String description;
    private int duration;
}
