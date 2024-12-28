package com.example.demo.license.dto;

import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import lombok.Data;

import java.util.Date;

@Data
public class LicenseResponse {
    private Long id;

    private String code;

    private UserResponse user;

    private ProductResponse product;

    private LicenseTypeResponse type;

    private Date firstActivationDate;

    private Date endingDate;

    private Boolean blocked;

    private Integer deviceCount;

    private UserResponse owner;

    private Integer duration;

    private String description;
}
