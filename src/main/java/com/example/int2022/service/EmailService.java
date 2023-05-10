package com.example.int2022.service;

import com.example.int2022.model.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@Service
public class EmailService {
    private JavaMailSender mailSender;

    public void sendConfirmationToken(String token, ApplicationUser newUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verify your account.";
        String senderName = "MovieApp";
        String verifyUrl = "http://localhost:8080/confirm?token=" + token;
        String mailContent = "<p>Please click the link below:</p>";
        mailContent += "<h3><a href=\"" + verifyUrl + "\">VERIFY</a></h3>";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("emirhantopcu50@gmail.com", senderName);
        helper.setTo(newUser.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    public void sendPasswordResetToken(String token, String email) throws MessagingException, UnsupportedEncodingException{
        String subject = "Password Change Request";
        String senderName = "MovieApp";
        String verifyUrl = "http://localhost:8080/resetPassword?token=" + token;
        String mailContent = "<p>Please click the link below to reset password:</p>";
        mailContent += "<h3><a href=\"" + verifyUrl + "\">RESET PASSWORD</a></h3>";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("emirhantopcu50@gmail.com", senderName);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }
}
