package com.example.int2022.service;

import com.example.int2022.dto.MovieDTO;
import com.example.int2022.dto.MovieRegisterDTO;
import com.example.int2022.dto.UserDTO;
import com.example.int2022.model.Movie;
import com.example.int2022.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MovieManagementService {
    private final MovieRepository movieRepository;
    public ResponseEntity<?> addMovie(MovieRegisterDTO movieRegisterDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        movieRepository.save(new Movie(movieRegisterDTO.getTitle(),
                movieRegisterDTO.getYear(),
                movieRegisterDTO.getDirected_by(),
                movieRegisterDTO.getGenre(),
                movieRegisterDTO.getRuntime_min()));
        responseMap.put("error", false);
        responseMap.put("title", movieRegisterDTO.getTitle());
        responseMap.put("message", "Movie registered successfully");
        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> deleteMovie(Long id){
        Map<String, Object> responseMap = new HashMap<>();
        String movie_title = movieRepository.findById(id).get().getTitle();
        movieRepository.deleteById(id);
        responseMap.put("error", false);
        responseMap.put("title", movie_title);
        responseMap.put("message", "Movie deleted successfully");
        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> getMovies() {
        Map<String, Object> responseMap = new HashMap<>();
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOs = new ArrayList<>();
        for (Movie movie:
             movies) {
            movieDTOs.add(new MovieDTO(movie.getId(), movie.getTitle(), movie.getYear(), movie.getDirected_by(),
            movie.getGenre(), movie.getRuntime_min()));
        }

        responseMap.put("error", false);
        responseMap.put("movies", movieDTOs);

        return ResponseEntity.ok(responseMap);
    }
}
