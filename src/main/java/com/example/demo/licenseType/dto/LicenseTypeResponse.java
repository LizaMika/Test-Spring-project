package com.example.demo.licenseType.dto;

import lombok.Data;

@Data
public class LicenseTypeResponse {

    private String name;

    private Integer defaultDuration;

    private String description;
}
