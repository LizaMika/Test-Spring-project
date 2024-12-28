package com.example.demo.license.mapper;

import com.example.demo.license.domain.License;
import com.example.demo.license.domain.LicenseHistory;
import com.example.demo.license.dto.LicenseHistoryResponse;
import com.example.demo.license.dto.LicenseResponse;
import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    @Mapping(target = "user", source = "user", qualifiedByName = "toUserResponse")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductResponse")
    @Mapping(target = "type", source = "type", qualifiedByName = "toLicenseTypeResponse")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "toUserResponse")
    LicenseResponse toLicenseResponse(License license);

    @Named("toLicenseTypeResponse")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "defaultDuration", source = "defaultDuration")
    @Mapping(target = "description", source = "description")
    LicenseTypeResponse toLicenseTypeResponse(LicenseType licenseType);

    @Named("toProductResponse")
    public abstract ProductResponse toProductResponse(Product product);

    @Named("toUserResponse")
    default UserResponse toUserResponse(User user) {
        if (user != null) {
            return UserResponse.builder()
                    .id(user.getId())
                    .role(user.getRole())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .build();
        } else {
            return null;
        }
    }
    @Mapping(target = "licenseId", source = "license.id")
    LicenseHistoryResponse toLicenseHistoryResponse(LicenseHistory licenseHistory);

}
