package com.example.demo.device.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class DeviceRequest {

    @NotBlank(message = "Device name cannot be empty")
    private String deviceName;

    @NotBlank(message = "Mac address cannot be empty")
    private String macAddress;

    @NotNull
    private Long userId;
}