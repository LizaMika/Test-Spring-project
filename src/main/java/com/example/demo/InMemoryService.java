package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryService {
    private final Map<Integer, User> userMap = new HashMap<>();
    private Integer counter = 0;

    public User addUser(NewUserDto newUserDto) {
        User user = new User(newUserDto.getName(), newUserDto.getSurname());
        userMap.put(counter, user);
        counter += 1;
        return user;
    }
    public User getUserById(Integer id) {
        User user = userMap.get(id);
        return user;
    }

}
