package com.example.int2022.service;

import com.example.int2022.model.Movie;
import com.example.int2022.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SearchService {
    private final MovieRepository movieRepository;

    public ResponseEntity<?> search(String query) {
        return ResponseEntity.ok(movieRepository.searchMovies(query));
    }
}
