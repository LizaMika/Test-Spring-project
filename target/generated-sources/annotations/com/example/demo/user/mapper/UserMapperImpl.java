package com.example.demo.user.mapper;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T06:01:42+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.login( user.getLogin() );
        userResponse.email( user.getEmail() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }
}
