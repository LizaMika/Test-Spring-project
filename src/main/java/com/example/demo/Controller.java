package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
    @GetMapping
    public List<User> getAll () {
        return userService.getAllUsers();
    }
    @PostMapping("/user")
    public User addUser(@RequestBody NewUserDto newUserDto) {
        return userService.addUser(newUserDto);
    }
    @PatchMapping("/{userId}")
    public User updateUserById (@RequestBody NewUserDto newUserDto, @PathVariable Long userId) {
        return userService.updateUserById(userId, newUserDto);
    }
    @DeleteMapping("/{userId}")
    public void deleteUserById (@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
