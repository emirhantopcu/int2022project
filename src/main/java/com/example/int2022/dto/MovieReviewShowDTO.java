package com.example.int2022.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MovieReviewShowDTO {
    private Long review_id;
    private String username;
    private String comment_body;
    private LocalDateTime time;
    private int likeCount;
}
