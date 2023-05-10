package com.example.int2022.service;

import com.example.int2022.dto.PasswordChangeDTO;
import com.example.int2022.model.ConfirmationToken;
import com.example.int2022.model.ForgotPasswordToken;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.repository.ForgotPasswordTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ForgotPasswordTokenService {
    ApplicationUserRepository applicationUserRepository;
    ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    EmailService emailService;
    public ResponseEntity<?> forgotPasswordRequest(String email) {
        Map<String, Object> responseMap = new HashMap<>();
        String token = UUID.randomUUID().toString();
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                applicationUserRepository.findApplicationUserByEmail(email));

        forgotPasswordTokenRepository.save(forgotPasswordToken);
//        try{
//            emailService.sendPasswordResetToken(token, email);
//        }catch (Exception e){
//            responseMap.put("error", true);
//            responseMap.put("email", email);
//            responseMap.put("message", e.getMessage());
//        }
        responseMap.put("error", false);
        responseMap.put("email", email);
        responseMap.put("message", "Password reset link sent to e-mail.");
        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> confirmPasswordChange(String token, PasswordChangeDTO passwordChangeDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("token not found"));

        if (forgotPasswordToken.getUsedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = forgotPasswordToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        applicationUserRepository.updatePassword(forgotPasswordToken.getApplicationUser().getId(),
                new BCryptPasswordEncoder().encode(passwordChangeDTO.getNew_password()));

        forgotPasswordTokenRepository.updateUsedAt(token, LocalDateTime.now());

        responseMap.put("error", false);
        responseMap.put("message", "Password successfully reset.");
        return ResponseEntity.ok(responseMap);
    }
}
