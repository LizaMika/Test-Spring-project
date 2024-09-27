package com.example.demo;

import lombok.*;

@Getter
@Setter
public class User {
    private String name;
    private String surname;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
