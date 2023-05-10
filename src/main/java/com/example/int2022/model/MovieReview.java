package com.example.int2022.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@ToString
public class MovieReview {

    public MovieReview(String comment_body, LocalDateTime sentTime, Long user_id, String owner_username, Long movie_id) {
        this.comment_body = comment_body;
        this.sentTime = sentTime;
        this.userId = user_id;
        this.ownerUsername = owner_username;
        this.movieId = movie_id;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String comment_body;

    private LocalDateTime sentTime;

    private Long userId;

    private String ownerUsername;

    private Long movieId;

    @ManyToMany
    @JoinTable(
            name = "liked_reviews",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<ApplicationUser> likedAccounts;

    private int likeCount = 0;

    public void addLikedAccount(ApplicationUser user){
        this.likedAccounts.add(user);
    }

}
