package com.example.MovieTicketBooking.dto.requestdtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDto
{

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "MovieShow ID is required")
    private Long movieShowId;

    @NotEmpty
    @Size(min = 1, message = "At least one seat must be selected")
    private List<Long> showSeatIds;
}
