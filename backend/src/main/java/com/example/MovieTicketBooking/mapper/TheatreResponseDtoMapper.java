package com.example.MovieTicketBooking.mapper;

import com.example.MovieTicketBooking.dto.responsedtos.TheatreResponseDto;
import com.example.MovieTicketBooking.model.Theatre;
import org.springframework.stereotype.Component;

@Component
public class TheatreResponseDtoMapper {
    public TheatreResponseDto theatreToDto(Theatre theatre){
        TheatreResponseDto theatreResponseDto = TheatreResponseDto.builder()
                .id(theatre.getId())
//                .moviet
                .build();




        return theatreResponseDto;
    }
}
