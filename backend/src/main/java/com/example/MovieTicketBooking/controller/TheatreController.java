package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.TheatreRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TheatreResponseDto;
import com.example.MovieTicketBooking.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    TheatreService theatreService;

    @PostMapping("/create-theatre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheatreResponseDto> createTheatre(@Valid @RequestBody TheatreRequestDto theatreRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.createTheatre(theatreRequestDto));
    }

    @GetMapping("/get-all-theatres")
    public List<TheatreResponseDto> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> getTheatreById(@Valid @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(theatreService.getTheatreDtoById(id));
    }
}
