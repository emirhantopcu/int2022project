package com.example.int2022.config;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.security.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(ApplicationUserRepository applicationUserRepository) {
        return args -> {
            ApplicationUser mariam = new ApplicationUser(
                    "mariamke",
                    "mariam@gmail.com",
                    new BCryptPasswordEncoder().encode("mariam123"),
                    UserRole.CUSTOMER);
            ApplicationUser ali = new ApplicationUser(
                    "ali123",
                    "ali123@gmail.com",
                    new BCryptPasswordEncoder().encode("123654"),
                    UserRole.CUSTOMER,
                    true);
            ApplicationUser bob = new ApplicationUser(
                    "bob",
                    "bob@gmail.com",
                    new BCryptPasswordEncoder().encode("bob123"),
                    UserRole.ADMIN,
                    true);


            applicationUserRepository.saveAll(List.of(mariam, ali, bob));
        };
    }
}