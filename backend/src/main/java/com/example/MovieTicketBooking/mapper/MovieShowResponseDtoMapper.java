package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.responsedtos.MovieShowResponseDto;
import com.example.MovieTicketBooking.dto.responsedtos.ShowSeatResponseDto;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.ShowSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieShowResponseDtoMapper
{
    @Autowired
    ShowSeatResponseDtoMapper mapper;

    public MovieShowResponseDto convertToMovieShowResponseDto(MovieShow movieShow){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<ShowSeatResponseDto> responseDtos = new ArrayList<>();

        for(ShowSeat seat : movieShow.getShowSeats()){
            responseDtos.add(mapper.convertToDto(seat));
        }

        return MovieShowResponseDto.builder()
                .id(movieShow.getId())
                .movieScheduleId(movieShow.getMovieSchedule().getId())
                .showDate(movieShow.getShowDate())
                .showTime(movieShow.getShowTime().format(formatter))
                .showSeats(responseDtos)
                .availableSeatsCount(movieShow.getAvailableSeatsCount())
                .theatreName(movieShow.getMovieSchedule().getTheatre().getName())
                .theatreLocation(movieShow.getMovieSchedule().getTheatre().getLocation())
                .build();
    }
}
