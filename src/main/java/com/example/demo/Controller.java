package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
public class Controller {

    private final InMemoryService inMemoryService = new InMemoryService();

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId) {
        return inMemoryService.getUserById(userId);
    }
    @PostMapping("/user")
    public User addUser(@RequestBody NewUserDto newUserDto) {
        return inMemoryService.addUser(newUserDto);
    }
}
