package com.example.MovieTicketBooking.dto.requestdtos;

import com.example.MovieTicketBooking.model.enums.SeatType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SeatRequestDto
        (
                @NotBlank(message = "Seat number is required")
                String seatNumber,

                @NotNull(message = "Seat type is required")
                SeatType seatType,

                @NotNull(message = "Price is required")
                @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
                BigDecimal price
        )
{
}
