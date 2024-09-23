package com.example.demo;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class User {
    private String name;
    private String surname;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
