package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
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
public class MovieScheduleOrchestrationService
{  // <-- better name

    private final MovieScheduleService movieScheduleService;
    private final MovieShowService movieShowService;
    private final MovieScheduleResponseMapper responseMapper;
    private final TheatreService theatreService;

    /**
     * Creates a new movie schedule and generates the associated shows for each day.
     *
     * @param dto The schedule request containing dates, theatre, and show timings.
     * @return The created schedule as a response DTO.
     */
    @Transactional
    public MovieScheduleResponseDto createScheduleWithShows(MovieScheduleRequestDto dto) {  // <-- clearer method name
        // 1. Create the schedule
        MovieScheduleResponseDto scheduleResponse = movieScheduleService.createMovieSchedule(dto);
        MovieSchedule movieSchedule = movieScheduleService.getScheduleById(scheduleResponse.getId());

        long numberOfDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        LocalDate currentDate = dto.getStartDate();
        Theatre theatre = theatreService.getTheatreEntityById(dto.getTheatreId());

        // 2. Generate shows for each day within the schedule
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
