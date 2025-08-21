package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.dto.requestdtos.MovieAssignmentRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.MovieAssignmentResponseDto;
import com.example.MovieTicketBooking.mapper.MovieAssignmentRequestMapper;
import com.example.MovieTicketBooking.mapper.MovieAssignmentResponseMapper;
import com.example.MovieTicketBooking.model.Movie;
import com.example.MovieTicketBooking.model.MovieAssignment;
import com.example.MovieTicketBooking.repository.MovieAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class MovieAssignmentService
{

    @Autowired
    MovieAssignmentRepository movieAssignmentRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    TheatreService theatreService;

    @Autowired
    MovieAssignmentRequestMapper requestMapper;

    @Autowired
    MovieAssignmentResponseMapper responseMapper;

    public MovieAssignmentResponseDto createMovieAssignment(MovieAssignmentRequestDto dto) {
        MovieAssignment assignment = requestMapper.convertToMovieAssignment(dto);
        MovieAssignment movieAssignment = movieAssignmentRepository.save(assignment);
        return responseMapper.convertTResponseDto(movieAssignment);
    }

    public MovieAssignment getAssignmentById(Long id) {
        return movieAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Movie assignment not found"));
    }

    public List<MovieAssignment> getMovieAssignments(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        return movieAssignmentRepository.findMovieAssignmentsByMovie(movie);
    }
}
