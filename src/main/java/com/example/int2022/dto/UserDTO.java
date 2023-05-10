package com.example.int2022.dto;

import com.example.int2022.model.Movie;
import com.example.int2022.security.UserRole;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String userRole;
    private final Collection<Movie> ownedMovies;
    private final boolean isEnabled;
    private final boolean isDeleted;
}
