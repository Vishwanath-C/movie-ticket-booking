package com.example.MovieTicketBooking.dto.requestdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TheatreRequestDto
        (
                @NotBlank(message = "Theatre name is required")
                String name,

                @NotBlank(message = "Location is required")
                String location,

                @Valid @NotEmpty(message = "Theatre must have at least one seat type")
                List<SeatTypeRequest> seatTypeRequests
               )
{
}