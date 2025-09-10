package com.example.MovieTicketBooking.dto.requestdtos;

import com.example.MovieTicketBooking.model.enums.SeatType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTypeRequest {

    @NotNull(message = "Seat type is required")
    private SeatType seatType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private BigDecimal price;

    @Min(value = 1, message = "Row count must be at least 1")
    private int rowCount;

    @Min(value = 1, message = "Seats per row must be at least 1")
    private int seatsPerRow;
}
