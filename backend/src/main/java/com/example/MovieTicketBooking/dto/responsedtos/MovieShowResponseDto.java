package com.example.MovieTicketBooking.dto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieShowResponseDto
{
    private Long id;
    private LocalDate showDate;
    private String showTime;
    private int availableSeatsCount;
    private Long movieScheduleId;
    private List<ShowSeatResponseDto> showSeats;
    private String theatreName;
    private String theatreLocation;

}
