package com.example.int2022.repository;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findApplicationUserByName(String username);
    ApplicationUser findApplicationUserByEmail(String email);
    ApplicationUser findApplicationUserById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ApplicationUser u " +
            "SET u.isEnabled = TRUE WHERE u.id = ?1")
    void enableUser(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ApplicationUser u " +
            "SET u.password = ?2 " +
            "WHERE u.id = ?1")
    void updatePassword(Long id, String encrypted_new_password);

    @Transactional
    @Modifying
    @Query("UPDATE ApplicationUser u " +
            "SET u.isDeleted = true " +
            "WHERE u.id = ?1")
    void deleteProfile(Long id);
}
