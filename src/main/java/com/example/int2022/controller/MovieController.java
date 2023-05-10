package com.example.int2022.controller;

import com.example.int2022.dto.MovieReviewDTO;
import com.example.int2022.model.MovieReview;
import com.example.int2022.repository.MovieRepository;
import com.example.int2022.service.MoviePurchaseService;
import com.example.int2022.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;
    private final MoviePurchaseService moviePurchaseService;

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMoviePage(@PathVariable("movieId") Long movieId){
        return movieService.getMovie(movieId);
    }

    @GetMapping("{movieId}/buy")
    public ResponseEntity<?> buyMovie(@PathVariable("movieId") Long movieId, HttpServletRequest request){
        return moviePurchaseService.buyMovie(movieId, request);
    }

}
