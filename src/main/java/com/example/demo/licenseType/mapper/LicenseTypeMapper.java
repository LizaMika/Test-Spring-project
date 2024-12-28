package com.example.demo.licenseType.mapper;

import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LicenseTypeMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "defaultDuration", source = "defaultDuration")
    @Mapping(target = "description", source = "description")
    LicenseTypeResponse toLicenseTypeResponse(LicenseType licenseType);
}
