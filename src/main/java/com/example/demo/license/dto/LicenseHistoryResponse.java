package com.example.demo.license.dto;

import com.example.demo.license.domain.License;
import com.example.demo.user.domain.User;
import lombok.Data;

import java.util.Date;

@Data
public class LicenseHistoryResponse {
    private Long id;

    private Long licenseId;

    private User user;

    private String status;

    private Date changeDate;

    private String description;
}
