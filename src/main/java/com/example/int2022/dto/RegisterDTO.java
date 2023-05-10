package com.example.int2022.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterDTO {
    private final String name;
    private final String email;
    private final String password;
}
