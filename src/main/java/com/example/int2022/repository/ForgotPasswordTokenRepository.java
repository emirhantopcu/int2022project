package com.example.int2022.repository;

import com.example.int2022.model.ConfirmationToken;
import com.example.int2022.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
    Optional<ForgotPasswordToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ForgotPasswordToken f " +
            "SET f.usedAt = ?2 " +
            "WHERE f.token = ?1")
    void updateUsedAt(String token, LocalDateTime now);
}
