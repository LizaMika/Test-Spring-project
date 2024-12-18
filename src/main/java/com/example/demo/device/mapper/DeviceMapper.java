package com.example.demo.device.mapper;

import com.example.demo.device.domain.Device;
import com.example.demo.device.dto.DeviceRequest;
import com.example.demo.device.dto.DeviceResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "deviceRequest.deviceName")
    @Mapping(target = "macAddress", source = "deviceRequest.macAddress")
    @Mapping(target = "user", source = "user")
    Device toDevice (DeviceRequest deviceRequest, User user);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "macAddress", source = "macAddress")
    @Mapping(target = "user", source = "user", qualifiedByName = "toUserResponse")
    DeviceResponse toDeviceResponse (Device device);

    @Named("toUserResponse")
    default UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .passwordHash(user.getPasswordHash())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();
    }
}
