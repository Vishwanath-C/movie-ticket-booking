package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieResponseDto;
import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.mapper.MovieDtoMapper;
import com.example.MovieTicketBooking.mapper.MovieResponseDtoMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService
{
    private final  MovieRepository movieRepository;
    private final   MovieDtoMapper movieDtoMapper;
    private final  MovieResponseDtoMapper movieResponseDtoMapper;

    public Movie createMovie(MovieRequestDto movieRequestDto) {
        movieRepository.findByTitle(movieRequestDto.getTitle())
                .ifPresent(movie -> {
                    throw new IllegalArgumentException("Movie with title '" + movieRequestDto.getTitle() + "' already exists");
                });


        Movie movie = movieDtoMapper.dtoToMovie(movieRequestDto);
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found!"));
    }


    public List<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieResponseDtoMapper::movieToDto)
                .collect(Collectors.toList());
    }


    public List<MovieResponseDto> getMoviesWithShows() {
        LocalDate today = LocalDate.now();

        return movieRepository.findAll().stream()
                .map(movie -> movie.getMovieSchedules().stream()
                        .filter(movieSchedule -> !today.isBefore(movieSchedule.getStartDate()) &&
                                !today.isAfter(movieSchedule.getEndDate()))
                        .findFirst()
                        .map(movieSchedule -> movieResponseDtoMapper.movieToDto(movie))
                        .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<MovieResponseDto> getCurrentShowingMovies() {
        LocalDate today = LocalDate.now();

        return movieRepository.findAll().stream()
                .map(movie -> {
                    // Get all dates for running schedules starting from today
                    List<LocalDate> runningDates = movie.getMovieSchedules().stream()
                            .filter(ma -> !today.isBefore(ma.getStartDate()) &&
                                    !today.isAfter(ma.getEndDate()))
                            .flatMap(ma -> {
                                LocalDate start = today.isAfter(ma.getStartDate()) ? today : ma.getStartDate();
                                LocalDate end = ma.getEndDate();
                                List<LocalDate> dates = new ArrayList<>();
                                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                                    dates.add(d);
                                }
                                return dates.stream();
                            })
                            .distinct() // remove duplicates if multiple schedules overlap
                            .toList();

                    if (runningDates.isEmpty()) return null;


                    // Map to DTO
                    MovieResponseDto dto = movieResponseDtoMapper.movieToDto(movie);
                    dto.setAvailableShowDates(runningDates);
                    dto.setCurrentlyRunning(true);
                    dto.setUpcoming(false);
                    return dto;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<MovieResponseDto> getUpcomingMovies() {
        LocalDate today = LocalDate.now();

        return movieRepository.findAll().stream()
                .map(movie -> {
                    List<LocalDate> runningDates = movie.getMovieSchedules().stream()
                            .filter(movieSchedule -> today.isBefore(movieSchedule.getStartDate()))
                            .flatMap(movieSchedule -> {
                                LocalDate start = movieSchedule.getStartDate();
                                LocalDate end = movieSchedule.getEndDate();
                                List<LocalDate> dates = new ArrayList<>();
                                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                                    dates.add(d);
                                }
                                return dates.stream();

                            })
                            .distinct()
                            .toList();
                    if (runningDates.isEmpty()) return null;


                    // Map to DTO
                    MovieResponseDto dto = movieResponseDtoMapper.movieToDto(movie);
                    dto.setAvailableShowDates(runningDates);
                    dto.setCurrentlyRunning(false);
                    dto.setUpcoming(true);
                    return dto;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Movie getByTitle(String title) {
        return movieRepository.findByTitle(title).
                orElseThrow(() -> new ResourceNotFoundException("Movie with title '" + title + "' not found!"));
    }

    public void deleteMovie(Long id) {
        movieRepository.delete(getMovieById(id));
    }

}
