package com.example.demo.user.controller;

import com.example.demo.user.dto.UserLoginModel;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.enumeration.Role;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import com.example.demo.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> userRegistration(@Valid @RequestBody UserRequest userDTO) {
        if (userService.existsByLogin(userDTO.getLogin())) {
            return ResponseEntity.status(400).body("Validation error: User with this login already exists");
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(400).body("Validation error: User with this email already exists");
        }

        User user = new User(userDTO.getLogin(), passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getEmail(), Role.ROLE_USER);
        userService.saveUser(user);

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok("Registration completed, JWT{Bearer " + token + "}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@Valid @RequestBody UserLoginModel userDTO) {
        User user = userService.findUserByLogin(userDTO.getLogin());

        if (user == null) {
            return ResponseEntity.status(400).body("Validation error: User with this login not found");
        }

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body("Validation error: Password is incorrect");
        }

        String token = jwtUtil.generateToken(userService.loadUserByUsername(user.getUsername()));

        return ResponseEntity.ok("Login completed, JWT{Bearer " + token + "}");
    }
}