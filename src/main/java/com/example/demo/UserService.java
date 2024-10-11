package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User addUser(NewUserDto newUserDto) {
        User user = new User(newUserDto.getName(), newUserDto.getSurname());
        return userRepository.save(user);
    }
    public User getUserById(Long id) {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userFromDb;
    }
    public User updateUserById(Long id, NewUserDto newUserDto) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional.ofNullable(newUserDto.getName()).ifPresent(userFromDb::setName);
        Optional.ofNullable(newUserDto.getSurname()).ifPresent(userFromDb::setSurname);

        return userRepository.save(userFromDb);
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
