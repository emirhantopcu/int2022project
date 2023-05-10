package com.example.int2022.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MovieRegisterDTO {
    private String title;
    private String year;
    private String directed_by;
    private String genre;
    private int runtime_min;
}
