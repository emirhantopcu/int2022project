package com.example.int2022.repository;

import com.example.int2022.model.Movie;
import com.example.int2022.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    List<MovieReview> findAllByMovieId(Long movie_id);


    @Transactional
    @Modifying
    @Query("UPDATE MovieReview r " +
            "SET r.likeCount = r.likeCount + 1 " +
            "WHERE r.id = ?1")
    void likeReview(Long reviewId);
}
