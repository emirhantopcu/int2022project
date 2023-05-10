package com.example.int2022.service;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.ConfirmationToken;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public void sendConfirmationToken(ApplicationUser newUser) throws MessagingException, UnsupportedEncodingException {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newUser);

        confirmationTokenRepository.save(confirmationToken);
        //emailService.sendConfirmationToken(token, newUser);
    }

    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());



    }

    public ConfirmationToken findToken(String token) {
        return confirmationTokenRepository.findByToken(token).get();
    }
}
