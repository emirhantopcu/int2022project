package com.example.int2022.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginDTO {
    private final String username;
    private final String password;
}
