package com.example.demo.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
public class ProductResponse {

    private String name;

    private boolean isBlocked;

}
