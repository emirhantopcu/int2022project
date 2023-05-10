package com.example.int2022.controller;

import com.example.int2022.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/{id}")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getProfilePage(@PathVariable("id") Long id, HttpServletRequest request){
        return profileService.getAccountInfo(id, request);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getPurchasedMovies(@PathVariable("id") Long id){
        return profileService.getPurchasedMovies(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id, HttpServletRequest request){

        return profileService.deleteAccount(id, request);
    }
}
