package com.example.MovieTicketBooking.dto.requestdtos;

import com.example.MovieTicketBooking.dto.SeatTypeRequest;

import java.util.List;

public record TheatreRequestDto
        (String name, String location, List<SeatTypeRequest> seatTypeRequests)
{
}