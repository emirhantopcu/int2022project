package com.example.int2022.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@ToString
public class Movie {
    public Movie(String title, String year, String directed_by, String genre, int runtime_min) {
        this.title = title;
        this.year = year;
        this.directed_by = directed_by;
        this.genre = genre;
        this.runtime_min = runtime_min;
    }

    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    private Long id;
    private String title;
    private String year;
    private String directed_by;
    private String genre;
    private int runtime_min;
}
