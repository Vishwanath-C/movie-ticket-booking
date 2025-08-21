package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.requestdtos.TheatreRequestDto;
import com.example.MovieTicketBooking.model.Theatre;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TheatreRequestDtoMapper {
    public Theatre dtoToTheatre(TheatreRequestDto theatreRequestDto){
        Theatre theatre = new Theatre();
        theatre.setName(theatreRequestDto.name());
        theatre.setLocation(theatreRequestDto.location());
        theatre.setMovieAssignments(new ArrayList<>());
        theatre.setSeats(new ArrayList<>());

        return theatre;
    }
}
