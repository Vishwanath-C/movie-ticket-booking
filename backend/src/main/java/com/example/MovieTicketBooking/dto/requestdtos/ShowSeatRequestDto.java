package com.example.MovieTicketBooking.dto.requestdtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatRequestDto {
    @NotNull(message = "Movie show ID is required")
    private Long movieShowId;

    @NotNull(message = "Seat ID is required")
    private Long seatId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private BigDecimal price;

    private boolean booked;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;
}
