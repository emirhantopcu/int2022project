package com.example.int2022.repository;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE " +
    "m.title LIKE CONCAT('%', :query, '%')" +
    "Or m.directed_by LIKE CONCAT('%', :query, '%')")
    List<Movie> searchMovies(String query);
}
