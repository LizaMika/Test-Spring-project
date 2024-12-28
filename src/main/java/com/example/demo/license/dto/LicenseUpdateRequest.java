package com.example.demo.license.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseUpdateRequest {

    @NotBlank(message = "Login cannot be empty")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "License key cannot be empty")
    private String licenseKey;
}