package com.example.int2022.service;

import com.example.int2022.dto.MovieDTO;
import com.example.int2022.model.Movie;
import com.example.int2022.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;


    public ResponseEntity<?> getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        MovieDTO movieDTO = new MovieDTO(movie.getId(), movie.getTitle(), movie.getYear(), movie.getDirected_by(),
                movie.getGenre(), movie.getRuntime_min());
        return ResponseEntity.ok(movieDTO);
    }

}
