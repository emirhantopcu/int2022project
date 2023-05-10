package com.example.int2022.controller;

import com.example.int2022.dto.UserDTO;
import com.example.int2022.dto.UserEditDTO;
import com.example.int2022.repository.ApplicationUserRepository;
import com.example.int2022.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {
    private final UserService userService;


    @GetMapping(path = "users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "user")
    public ResponseEntity<?> getUser(@RequestParam("id") Long id){
        return userService.getUser(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(@RequestParam("id") Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("edit")
    public ResponseEntity<?> editUser(@RequestBody UserEditDTO userEditDTO, @RequestParam("id") Long id){
        return userService.editUser(id, userEditDTO);
    }
}

