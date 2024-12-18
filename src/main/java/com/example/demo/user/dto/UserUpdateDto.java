package com.example.demo.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String login;

    private String password;

    private String email;
}
