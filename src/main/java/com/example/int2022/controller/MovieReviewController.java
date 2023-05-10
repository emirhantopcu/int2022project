package com.example.int2022.controller;

import com.example.int2022.dto.MovieReviewDTO;
import com.example.int2022.model.MovieReview;
import com.example.int2022.service.MovieReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/movie")
public class MovieReviewController {
    private final MovieReviewService movieReviewService;

    @PostMapping("/{movieId}/postReview")
    public ResponseEntity<?> reviewMovie(@PathVariable("movieId") Long movieId,
                                         @RequestBody MovieReviewDTO movieReviewDTO,
                                         HttpServletRequest request){
        return movieReviewService.reviewMovie(movieId, movieReviewDTO, request);
    }

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable("movieId") Long movieId){
        return movieReviewService.getReviews(movieId);
    }

    @PutMapping("/reviews/likeReview")
    public ResponseEntity<?> likeReview(@RequestParam("reviewId") Long reviewId, HttpServletRequest request){
        return movieReviewService.likeReview(reviewId, request);
    }
}
