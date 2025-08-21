package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.SeatTypeRequest;
import com.example.MovieTicketBooking.dto.requestdtos.SeatRequestDto;
import com.example.MovieTicketBooking.dto.requestdtos.TheatreRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TheatreResponseDto;
import com.example.MovieTicketBooking.mapper.TheatreRequestDtoMapper;
import com.example.MovieTicketBooking.mapper.TheatreResponseDtoMapper;
import com.example.MovieTicketBooking.model.Seat;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.model.enums.SeatType;
import com.example.MovieTicketBooking.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreService {
    
    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    TheatreRequestDtoMapper theatreRequestDtoMapper;

    @Autowired
    TheatreResponseDtoMapper theatreResponseDtoMapper;


//    public Theatre createTheatre(TheatreRequestDto theatreRequestDto){
//        Theatre theatre = theatreRequestDtoMapper.dtoToTheatre(theatreRequestDto);
//        return theatreRepository.save(theatre);
//    }

    @Transactional
    public TheatreResponseDto createTheatre(TheatreRequestDto theatreRequestDto){
        Theatre theatre = Theatre.builder()
                .name(theatreRequestDto.name())
                .location(theatreRequestDto.location())
                .build();

        List<Seat> seats = new ArrayList<>();

        for (SeatTypeRequest seatTypeRequest : theatreRequestDto.seatTypeRequests()) {
            for (char row = 'A'; row < 'A' + seatTypeRequest.getRowCount(); row++) {
                for (int i = 1; i <= seatTypeRequest.getSeatsPerRow(); i++) {

                    String seatNumber = seatTypeRequest.getSeatType() == SeatType.GOLD ? ("G" + row + i)
                            : ("N" + row + i);

                    Seat seat = Seat.builder()
                            .seatType(seatTypeRequest.getSeatType())
                            .seatNumber(seatNumber)
                            .theatre(theatre)
                            .price(seatTypeRequest.getPrice())
                            .build();

                    seats.add(seat);
                }
            }
        }

//        List<Seat> seats = theatreRequestDto.seats().stream()
//                .map(dto -> Seat.builder()
//                        .seatNumber(dto.seatNumber())
//                        .seatType(dto.seatType())
//                        .price(dto.price())
//                        .theatre(theatre)
//                        .build())
//                .collect(Collectors.toList());

        theatre.setSeats(seats);

        Theatre savedTheatre = theatreRepository.save(theatre);

        List<SeatRequestDto> seatDtos = savedTheatre.getSeats().stream().map(
                seat -> new SeatRequestDto(seat.getSeatNumber(), seat.getSeatType(), seat.getPrice())
        ).collect(Collectors.toList());

        return new TheatreResponseDto(
                savedTheatre.getId(),
                savedTheatre.getName(),
                savedTheatre.getLocation(),
                seatDtos
        );
    }

     public List<TheatreResponseDto> getAllTheatres(){
        List<Theatre> theatres = theatreRepository.findAll();
        List<TheatreResponseDto> responseDtos = new ArrayList<>();

        for(Theatre theatre : theatres){
//            TheatreResponseDto dto = theatreResponseDtoMapper.(theatre);
            List<SeatRequestDto> seatDtos = theatre.getSeats().stream().map(
                    seat -> new SeatRequestDto(seat.getSeatNumber(), seat.getSeatType(), seat.getPrice())
            ).collect(Collectors.toList());



            TheatreResponseDto dto = new TheatreResponseDto(
                    theatre.getId(),
                    theatre.getName(),
                    theatre.getLocation(),
                    seatDtos
            );
            responseDtos.add(dto);
        }
        return responseDtos;
    }

    public Theatre getTheatreById(Long id){
        return theatreRepository.findById(id).get();
    }

    public Theatre getTheatreByName(String name){
        return theatreRepository.findByName(name);
    }

    public void deleteTheatre(Long id){
        theatreRepository.delete(getTheatreById(id));
    }


}
