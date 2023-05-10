package com.example.int2022.service;

import com.example.int2022.dto.AccountDTO;
import com.example.int2022.model.ApplicationUser;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.repository.MovieRepository;
import com.example.int2022.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ApplicationUserRepository applicationUserRepository;
    private final MovieRepository movieRepository;
    private final JwtTokenUtil jwtTokenUtil;


    public ResponseEntity<?> deleteAccount(Long id, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if (applicationUserRepository.findApplicationUserByName(username).getId().equals(id)){
            applicationUserRepository.deleteProfile(id);
            responseMap.put("error", false);
            responseMap.put("message", "Account deleted successfully.");
            responseMap.put("id", id);
            return ResponseEntity.ok(responseMap);
        }else {
            responseMap.put("error", true);
            responseMap.put("message", "Unauthorized.");
            responseMap.put("id", id);
            return ResponseEntity.status(403).body(responseMap);
        }
    }

    public ResponseEntity<?> getPurchasedMovies(Long id) {
        return ResponseEntity.ok(applicationUserRepository.findApplicationUserById(id).getOwnedMovies());
    }

    public ResponseEntity<?> getAccountInfo(Long id, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

        ApplicationUser user = applicationUserRepository.findById(id).get();

        AccountDTO accountDTO = new AccountDTO(user.getId(), user.getName(), user.getEmail(), false);

        if (applicationUserRepository.findApplicationUserByName(username).getId().equals(id)){
            accountDTO.setOwner(true);
        }

        return ResponseEntity.ok(accountDTO);
    }
}
