package com.example.MovieTicketBooking.controller;

import com.example.MovieTicketBooking.dto.requestdtos.TheatreRequestDto;
import com.example.MovieTicketBooking.dto.responsedtos.TheatreResponseDto;
import com.example.MovieTicketBooking.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5176"} )
@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    TheatreService theatreService;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-theatre")
    public ResponseEntity<TheatreResponseDto> postMethodName(@RequestBody TheatreRequestDto theatreRequestDto) {
        System.out.println("Inside ticket post controller");
        System.out.println(theatreRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.createTheatre(theatreRequestDto));
    }
//    public ResponseEntity<Theatre> postMethodName(@RequestBody TheatreRequestDto theatreRequestDto) {
//        System.out.println("Inside ticket post controller");
//        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.createTheatre(theatreRequestDto));
//    }

    @GetMapping("/get-all-theatres")
    public List<TheatreResponseDto> getAllTheatres() {
        System.out.println("Inside get all theatres controller");
        return theatreService.getAllTheatres();
    }
}
