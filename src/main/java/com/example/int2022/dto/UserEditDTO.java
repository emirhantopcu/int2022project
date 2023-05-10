package com.example.int2022.dto;

import com.example.int2022.model.Movie;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserEditDTO {
    private final String name;
    private final String email;
    private final String password;
    private final String userRole;
    private final boolean changeIsEnabled;
    private final boolean changeIsDeleted;
}
