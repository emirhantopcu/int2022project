package com.example.int2022.service;

import com.example.int2022.dto.LoginDTO;
import com.example.int2022.dto.RegisterDTO;
import com.example.int2022.model.ApplicationUser;
import com.example.int2022.model.ConfirmationToken;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.security.UserRole;
import com.example.int2022.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@AllArgsConstructor
@Service
public class AuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final ConfirmationTokenService confirmationTokenService;
    final AuthenticationManager authenticationManager;

    public ResponseEntity<?> registerUser(RegisterDTO request){
        Map<String, Object> responseMap = new HashMap<>();
        ApplicationUser newUser = new ApplicationUser(request.getName(),
                request.getEmail(), new BCryptPasswordEncoder().encode(request.getPassword()),
                UserRole.CUSTOMER);
        try {
            applicationUserRepository.save(newUser);
            confirmationTokenService.sendConfirmationToken(newUser);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getName());
            String token = jwtTokenUtil.generateToken(userDetails);
            responseMap.put("error", false);
            responseMap.put("username", request.getName());
            responseMap.put("message", "Account created successfully");
            responseMap.put("token", token);
            return ResponseEntity.ok(responseMap);
        } catch (DataIntegrityViolationException e) {
            String exceptionMessage = e.getMostSpecificCause().getMessage();
            String duplicateCredential = exceptionMessage.split("Key \\(")[1].split("\\)")[0];
            responseMap.put("error", true);
            responseMap.put("username", request.getName());
            responseMap.put("message", String.format("This %s is already taken.", duplicateCredential));
            return ResponseEntity.status(409).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    public ResponseEntity<?> loginUser(LoginDTO request){
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername()
                    , request.getPassword()));
            if (applicationUserRepository.findApplicationUserByName(request.getUsername()).isDeleted()){
                responseMap.put("error", true);
                responseMap.put("message", "This account is deleted.");
                responseMap.put("username", request.getUsername());
                return ResponseEntity.status(403).body(responseMap);
            }
            if (auth.isAuthenticated()) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(403).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Bad Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    public ResponseEntity<?> confirmToken(String token) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            confirmationTokenService.confirmToken(token);
            ConfirmationToken confirmationToken = confirmationTokenService.findToken(token);
            enableUser(confirmationToken.getApplicationUser());
        } catch (Exception e){
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.status(500).body(responseMap);
        }
        responseMap.put("error", false);
        responseMap.put("message", "Confirmation token is verified and user is enabled.");
        return ResponseEntity.status(200).body(responseMap);
    }

    public void enableUser(ApplicationUser applicationUser) {
        applicationUserRepository.enableUser(applicationUser.getId());
    }
}
