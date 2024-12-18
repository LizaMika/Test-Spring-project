package com.example.demo.device.dto;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceResponse {

    private String name;

    private String macAddress;

    private UserResponse user;

}
