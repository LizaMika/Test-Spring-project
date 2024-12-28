package com.example.demo.license.mapper;

import com.example.demo.license.domain.License;
import com.example.demo.license.domain.LicenseHistory;
import com.example.demo.license.dto.LicenseHistoryResponse;
import com.example.demo.license.dto.LicenseResponse;
import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T06:10:40+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class LicenseMapperImpl implements LicenseMapper {

    @Override
    public LicenseResponse toLicenseResponse(License license) {
        if ( license == null ) {
            return null;
        }

        LicenseResponse licenseResponse = new LicenseResponse();

        licenseResponse.setUser( toUserResponse( license.getUser() ) );
        licenseResponse.setProduct( toProductResponse( license.getProduct() ) );
        licenseResponse.setType( toLicenseTypeResponse( license.getType() ) );
        licenseResponse.setOwner( toUserResponse( license.getOwner() ) );
        licenseResponse.setId( license.getId() );
        licenseResponse.setCode( license.getCode() );
        licenseResponse.setFirstActivationDate( license.getFirstActivationDate() );
        licenseResponse.setEndingDate( license.getEndingDate() );
        licenseResponse.setBlocked( license.getBlocked() );
        licenseResponse.setDeviceCount( license.getDeviceCount() );
        licenseResponse.setDuration( license.getDuration() );
        licenseResponse.setDescription( license.getDescription() );

        return licenseResponse;
    }

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

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setName( product.getName() );

        return productResponse;
    }

    @Override
    public LicenseHistoryResponse toLicenseHistoryResponse(LicenseHistory licenseHistory) {
        if ( licenseHistory == null ) {
            return null;
        }

        LicenseHistoryResponse licenseHistoryResponse = new LicenseHistoryResponse();

        licenseHistoryResponse.setLicenseId( licenseHistoryLicenseId( licenseHistory ) );
        licenseHistoryResponse.setId( licenseHistory.getId() );
        licenseHistoryResponse.setUser( licenseHistory.getUser() );
        licenseHistoryResponse.setStatus( licenseHistory.getStatus() );
        licenseHistoryResponse.setChangeDate( licenseHistory.getChangeDate() );
        licenseHistoryResponse.setDescription( licenseHistory.getDescription() );

        return licenseHistoryResponse;
    }

    private Long licenseHistoryLicenseId(LicenseHistory licenseHistory) {
        if ( licenseHistory == null ) {
            return null;
        }
        License license = licenseHistory.getLicense();
        if ( license == null ) {
            return null;
        }
        Long id = license.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
