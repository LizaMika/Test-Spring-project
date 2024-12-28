package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("Getting user by id: {}", id);
        UserResponse user = userService.getUserById(id);
        log.info("Got user: {}", user);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateDto user, @AuthenticationPrincipal UserDetails userDetails) {
            String findUsername = userDetails.getUsername();
            User currentUser = userService.findUserByLogin(findUsername);

            if (user.getLogin() != null && !user.getLogin().equals(currentUser.getLogin())) {
                if (userService.existsByLogin(user.getLogin())) {
                    throw new EntityExistsException("Login already exists");
                }

                currentUser.setLogin(user.getLogin());
            }

            if (user.getEmail() != null && !user.getEmail().equals(currentUser.getEmail())) {
                if (userService.existsByEmail(user.getEmail())) {
                    throw new EntityExistsException("Email already exists");
                }
                currentUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null) {
                currentUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            }

            UserResponse userResponse = userService.saveUser(currentUser);

            return ResponseEntity.ok(userResponse);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}