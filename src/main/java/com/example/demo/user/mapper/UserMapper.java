package com.example.demo.user.mapper;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
