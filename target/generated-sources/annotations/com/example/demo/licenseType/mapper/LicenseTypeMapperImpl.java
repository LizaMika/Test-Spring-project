package com.example.demo.licenseType.mapper;

import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-17T07:56:41+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class LicenseTypeMapperImpl implements LicenseTypeMapper {

    @Override
    public LicenseTypeResponse toLicenseTypeResponse(LicenseType licenseType) {
        if ( licenseType == null ) {
            return null;
        }

        LicenseTypeResponse licenseTypeResponse = new LicenseTypeResponse();

        licenseTypeResponse.setName( licenseType.getName() );
        licenseTypeResponse.setDefaultDuration( licenseType.getDefaultDuration() );
        licenseTypeResponse.setDescription( licenseType.getDescription() );

        return licenseTypeResponse;
    }
}
