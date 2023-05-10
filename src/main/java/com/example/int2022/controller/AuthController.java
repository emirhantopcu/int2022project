package com.example.int2022.controller;

import com.example.int2022.dto.LoginDTO;
import com.example.int2022.dto.PasswordChangeDTO;
import com.example.int2022.dto.RegisterDTO;
import com.example.int2022.service.ForgotPasswordTokenService;
import com.example.int2022.service.JwtUserDetailsService;
import com.example.int2022.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class AuthController {
    private final AuthService authService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final ForgotPasswordTokenService forgotPasswordTokenService;

    @Autowired
    public AuthController(AuthService authService,
                          JwtUserDetailsService jwtUserDetailsService,
                          ForgotPasswordTokenService forgotPasswordTokenService) {
        this.authService = authService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.forgotPasswordTokenService = forgotPasswordTokenService;
    }

    @GetMapping(path = "/register")
    public String registerPage(){
        return "register page";
    }

    @CrossOrigin
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO request){
        return authService.registerUser(request);
    }

    @GetMapping(path = "/login")
    public String loginPage(){
        return "login page";
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request){
        return authService.loginUser(request);
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<?> login(@RequestParam("token") String token){
        return authService.confirmToken(token);
    }

    @GetMapping(path = "/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        return forgotPasswordTokenService.forgotPasswordRequest(email);
    }

    @PutMapping(path = "/resetPassword")
    public ResponseEntity<?> forgotPasswordChange(@RequestParam("token")String token,
                                                  @RequestBody PasswordChangeDTO passwordChangeDTO){
        return forgotPasswordTokenService.confirmPasswordChange(token, passwordChangeDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/customers/{id}")
    public String getUsers(@PathVariable("id") String id){
        return jwtUserDetailsService.loadUserByUsername(id).toString();
    }





}
