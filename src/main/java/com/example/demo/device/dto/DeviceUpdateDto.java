package com.example.demo.device.dto;

import lombok.Data;

@Data
public class DeviceUpdateDto {
    private String deviceName;

    private String macAddress;

    private Long userId;
}
