package com.example.demo.user.dto;

import com.example.demo.user.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;

    private String login;

    private String email;

    private Role role;

}
