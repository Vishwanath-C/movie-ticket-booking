package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.mapper.MovieScheduleRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieScheduleResponseMapper;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.MovieShow;
import com.example.MovieTicketBooking.model.Theatre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class MovieSharedService
{
    private final MovieScheduleService movieScheduleService;
    private final MovieShowService movieShowService;
    private final MovieScheduleRequestMapper requestMapper;
    private final MovieScheduleResponseMapper responseMapper;
    private final TheatreService theatreService;

    @Transactional
    public MovieScheduleResponseDto createMovieShows(MovieScheduleRequestDto dto) {
        MovieScheduleResponseDto movieScheduleResponseDto = movieScheduleService.createMovieSchedule(dto);
        MovieSchedule movieSchedule = movieScheduleService.getScheduleById(movieScheduleResponseDto.getId());
        long numberOfDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        LocalDate currentDate = dto.getStartDate();
        Theatre theatre = theatreService.getTheatreEntityById(dto.getTheatreId());


        for (int i = 0; i < numberOfDays; i++) {
            for (int j = 0; j < dto.getNumberOfShowsPerDay(); j++) {
                MovieShow movieShow = MovieShow.builder()
                        .showTime(dto.getShowTimings().get(j))
                        .showDate(currentDate)
                        .movieSchedule(movieSchedule)
                        .availableSeatsCount(movieSchedule.getTheatre().getSeats().size())
                        .build();
                movieShowService.createMovieShow(movieShow, theatre);
            }
            currentDate = currentDate.plusDays(1);
        }
        return responseMapper.convertTResponseDto(movieSchedule);
    }

}
