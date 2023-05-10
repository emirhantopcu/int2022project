package com.example.int2022.service;

import com.example.int2022.dto.MovieReviewDTO;
import com.example.int2022.dto.MovieReviewShowDTO;
import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.MovieReview;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.repository.MovieRepository;
import com.example.int2022.repository.MovieReviewRepository;
import com.example.int2022.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MovieReviewService {
    private final MovieReviewRepository movieReviewRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationUserRepository applicationUserRepository;
    private final MovieRepository movieRepository;

    public ResponseEntity<?> reviewMovie(Long movieId, MovieReviewDTO movieReviewDTO, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        movieReviewRepository.save(new MovieReview(movieReviewDTO.getComment_body(),
                LocalDateTime.now(),
                applicationUserRepository.findApplicationUserByName(username).getId(),
                username,
                movieRepository.findById(movieId).get().getId()));
        responseMap.put("error", false);
        responseMap.put("movie_id", movieId);
        responseMap.put("message", "Review posted successfully");
        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> getReviews(Long movieId) {
        List<MovieReview> reviews = movieReviewRepository.findAllByMovieId(movieId);
        List<MovieReviewShowDTO> responseDTOs = new ArrayList<>();
        for (MovieReview review:
             reviews) {
            responseDTOs.add(new MovieReviewShowDTO(review.getId(), review.getOwnerUsername(),
                    review.getComment_body(), review.getSentTime(), review.getLikeCount()));
        }
        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<?> likeReview(Long reviewId, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        MovieReview movieReview = movieReviewRepository.findById(reviewId).get();
        ApplicationUser user = applicationUserRepository.findApplicationUserByName(username);
        if (movieReview.getLikedAccounts().contains(user)){
            responseMap.put("error", true);
            responseMap.put("review_id", reviewId);
            responseMap.put("message", "Review is already liked.");
            return ResponseEntity.ok(responseMap);
        }
        movieReviewRepository.likeReview(reviewId);
        movieReview.addLikedAccount(user);
        movieReviewRepository.save(movieReview);
        responseMap.put("error", false);
        responseMap.put("review_id", reviewId);
        responseMap.put("message", "Review liked successfully");
        return ResponseEntity.ok(responseMap);
    }
}
