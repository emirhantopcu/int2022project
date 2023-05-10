package com.example.int2022.service;

import com.example.int2022.dto.UserDTO;
import com.example.int2022.dto.UserEditDTO;
import com.example.int2022.model.ApplicationUser;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.security.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserService {

    ApplicationUserRepository applicationUserRepository;

    public ResponseEntity<?> getAllUsers(){
        Map<String, Object> responseMap = new HashMap<>();
        List<ApplicationUser> users = applicationUserRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (ApplicationUser user :
             users) {
            UserDTO dto = new UserDTO(user.getId(), user.getName(),
                    user.getEmail(), user.getPassword(),
                    user.getUserRole().name(), user.getOwnedMovies(), user.isEnabled(), user.isDeleted());
            userDTOs.add(dto);
        }

        responseMap.put("error", false);
        responseMap.put("users", userDTOs);

        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Map<String, Object> responseMap = new HashMap<>();
        applicationUserRepository.deleteProfile(id);

        responseMap.put("error", false);
        responseMap.put("id", id);
        responseMap.put("message", "User account successfully deleted.");

        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> getUser(Long id) {
        Map<String, Object> responseMap = new HashMap<>();
        ApplicationUser user = applicationUserRepository.findById(id).get();

        UserDTO dto = new UserDTO(user.getId(), user.getName(),
                user.getEmail(), user.getPassword(),
                user.getUserRole().name(), user.getOwnedMovies(), user.isEnabled(), user.isDeleted());
        responseMap.put("error", false);
        responseMap.put("user", dto);

        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> editUser(Long id, UserEditDTO userEditDTO) {
        Map<String, Object> responseMap = new HashMap<>();

        ApplicationUser user = applicationUserRepository.findApplicationUserById(id);

        if (userEditDTO.getName() != null){
            user.setName(userEditDTO.getName());
        }
        if (userEditDTO.getEmail() != null){
            user.setEmail(userEditDTO.getEmail());
        }
        if (userEditDTO.getPassword() != null){
            user.setPassword(new BCryptPasswordEncoder().encode(userEditDTO.getPassword()));
        }
        if (userEditDTO.getUserRole() != null){
            user.setUserRole(UserRole.valueOf(userEditDTO.getUserRole()));
        }
        if (userEditDTO.isChangeIsEnabled()){
            user.setEnabled(!user.isEnabled());
        }
        if (userEditDTO.isChangeIsDeleted()){
            user.setDeleted(!user.isDeleted());
        }

        applicationUserRepository.save(user);

        responseMap.put("error", false);
        responseMap.put("id", id);
        responseMap.put("message", "User account successfully edited.");

        return ResponseEntity.ok(responseMap);
    }
}
