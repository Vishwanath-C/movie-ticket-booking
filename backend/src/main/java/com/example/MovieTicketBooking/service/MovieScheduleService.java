package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieScheduleRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieScheduleResponseDto;
import com.example.MovieTicketBooking.exception.InvalidDateException;
import com.example.MovieTicketBooking.mapper.MovieScheduleRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieScheduleResponseMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieSchedule;
import com.example.MovieTicketBooking.model.Theatre;
import com.example.MovieTicketBooking.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieScheduleService
{
    private final MovieScheduleRepository movieScheduleRepository;
    private final MovieService movieService;
    private final TheatreService theatreService;
    private final MovieScheduleRequestMapper requestMapper;
    private final MovieScheduleResponseMapper responseMapper;

    /**
     * Creates a new movie schedule from the provided DTO.
     * Throws an exception if the end date is before the start date.
     *
     * @param dto The request DTO containing the schedule details.
     * @return The created movie schedule as a response DTO.
     * @throws InvalidDateException If the start date is after the end date.
     */
    public MovieScheduleResponseDto createMovieSchedule(MovieScheduleRequestDto dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidDateException("End date must be after or equal to start date");
        }

        MovieSchedule schedule = requestMapper.convertToMovieSchedule(dto);
        MovieSchedule movieSchedule = movieScheduleRepository.save(schedule);

        return responseMapper.convertTResponseDto(movieSchedule);
    }

    /**
     * Retrieves a movie schedule by its ID.
     *
     * @param id The ID of the movie schedule.
     * @return The movie schedule.
     * @throws ResourceAccessException If the schedule is not found.
     */
    public MovieSchedule getScheduleById(Long id) {
        return movieScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Movie schedule not found"));
    }

    /**
     * Retrieves a list of movie schedules for a given movie ID.
     *
     * @param movieId The ID of the movie.
     * @return A list of movie schedules for the given movie.
     */
    public List<MovieSchedule> getMovieSchedules(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        return movieScheduleRepository.findAllByMovie(movie);
    }

    /**
     * Retrieves the movie schedule by theatre ID.
     * Only returns the first schedule found for the theatre.
     *
     * @param theatreId The ID of the theatre.
     * @return A movie schedule response DTO for the given theatre.
     * @throws ResourceAccessException If no schedule is found for the theatre.
     */
    public MovieScheduleResponseDto getMovieSchedulesByTheatre(Long theatreId) {
        Theatre theatre = theatreService.getTheatreEntityById(theatreId);

        MovieSchedule movieSchedule = movieScheduleRepository.findAllByTheatre(theatre)
                .stream().findFirst()
                .orElseThrow(() -> new ResourceAccessException("Movie schedule not found for this theatre"));

        MovieScheduleResponseDto responseDto = MovieScheduleResponseDto.builder()
                .id(movieSchedule.getId())
                .movieTitle(movieSchedule.getMovie().getTitle())
                .theatreName(movieSchedule.getTheatre().getName())
                .startDate(movieSchedule.getStartDate())
                .endDate(movieSchedule.getEndDate())
                .build();
        return responseDto;
    }
}
