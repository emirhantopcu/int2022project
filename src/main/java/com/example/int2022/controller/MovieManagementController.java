package com.example.int2022.controller;

import com.example.int2022.dto.MovieDTO;
import com.example.int2022.dto.MovieRegisterDTO;
import com.example.int2022.model.Movie;
import com.example.int2022.service.MovieManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/manageMovies")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MovieManagementController {
    private final MovieManagementService movieManagementService;

    @GetMapping
    public ResponseEntity<?> getMovies(){
        return movieManagementService.getMovies();
    }

    @PostMapping("addMovie")
    public ResponseEntity<?> addMovie(@RequestBody MovieRegisterDTO movieRegisterDTO){
        return movieManagementService.addMovie(movieRegisterDTO);
    }

    @DeleteMapping("deleteMovie")
    public ResponseEntity<?> deleteMovie(@RequestParam("id") Long id){
        return movieManagementService.deleteMovie(id);
    }
}
