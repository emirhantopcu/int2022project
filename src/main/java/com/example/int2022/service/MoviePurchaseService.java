package com.example.int2022.service;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.Movie;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.repository.MovieRepository;
import com.example.int2022.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MoviePurchaseService {
    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationUserRepository applicationUserRepository;
    private final MovieRepository movieRepository;

    public ResponseEntity<?> buyMovie(Long movieId, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        ApplicationUser user = applicationUserRepository.findApplicationUserByName(username);
        if(user.getOwnedMovies().contains(movieRepository.findById(movieId).get())){
            responseMap.put("error", true);
            responseMap.put("movie_id", movieId);
            responseMap.put("message", "Movie is already owned.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMap);
        }
        user.addOwnedMovie(movieRepository.findById(movieId).get());
        applicationUserRepository.save(user);
        responseMap.put("error", false);
        responseMap.put("movie_id", movieId);
        responseMap.put("message", "Movie bought successfully");
        return ResponseEntity.ok(responseMap);
    }
}
