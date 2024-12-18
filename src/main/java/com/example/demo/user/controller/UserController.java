package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.service.UserService;
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
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.info("Getting user by id: {}", id);
        User user = userService.getUserById(id);
        log.info("Got user: {}", user);
        return ResponseEntity.status(200).body("User: " + user.toString());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto user, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String findUsername = userDetails.getUsername();
            User currentUser = userService.findUserByLogin(findUsername);

            if (user.getLogin() != null && !user.getLogin().equals(currentUser.getLogin())) {
                if (userService.existsByLogin(user.getLogin())) {
                    return ResponseEntity.status(400).body("Validation error: login already exists");
                }

                currentUser.setLogin(user.getLogin());
            }

            if (user.getEmail() != null && !user.getEmail().equals(currentUser.getEmail())) {
                if (userService.existsByEmail(user.getEmail())) {
                    return ResponseEntity.status(400).body("Validation error: email already exists");
                }
                currentUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null) {
                currentUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            }

            userService.saveUser(currentUser);

            return ResponseEntity.status(200).body("User with login: " + currentUser.getLogin() + " updated");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("User with id: " + id + " deleted");
    }
}