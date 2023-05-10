package com.example.int2022.model;

import com.example.int2022.security.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Indexed;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@ToString
public class ApplicationUser {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(unique=true, nullable = false)
    private String name;
    @Pattern(regexp="/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/", message="Please provide a valid email address")
    @Column(unique=true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToMany
    @JoinTable(
            name = "purchased_films",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Collection<Movie> ownedMovies = new ArrayList<>();


    private boolean isEnabled = false;

    private boolean isDeleted = false;

    public ApplicationUser(String name, String email, String password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public ApplicationUser(String name, String email, String password, UserRole userRole, boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
    }

    public void addOwnedMovie(Movie movie){
        this.ownedMovies.add(movie);
    }

}
