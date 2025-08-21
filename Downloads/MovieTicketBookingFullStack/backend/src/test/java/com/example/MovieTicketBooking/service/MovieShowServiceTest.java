package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.mapper.MovieShowResponseDtoMapper;
import com.example.MovieTicketBooking.repository.MovieShowRepository;
import org.mockito.Mock;

public class MovieShowServiceTest
{
    @Mock
    MovieShowRepository movieShowRepository;

    @Mock
    MovieAssignmentService movieAssignmentService;

    @Mock
    MovieShowResponseDtoMapper movieShowResponseDtoMapper;
}
