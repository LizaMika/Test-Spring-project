package com.example.demo.licenseType.service;

import com.example.demo.errors.NotFoundException;
import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.licenseType.dto.LicenseTypeRequest;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import com.example.demo.licenseType.dto.LicenseTypeUpdateDto;
import com.example.demo.license.repository.LicenseTypeRepository;
import com.example.demo.licenseType.mapper.LicenseTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LicenseTypeService {

    private final LicenseTypeRepository licenseTypeRepository;
    private final LicenseTypeMapper licenseTypeMapper;

    public LicenseType getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("License type with id %s not found", id)));
    }

    public LicenseTypeResponse createLicenseType(LicenseTypeRequest licenseTypeRequest) {

        LicenseType licenseType = LicenseType.builder()
                .name(licenseTypeRequest.getName())
                .defaultDuration(licenseTypeRequest.getDefaultDuration())
                .description(licenseTypeRequest.getDescription())
                .build();

        return licenseTypeMapper.toLicenseTypeResponse(licenseTypeRepository.save(licenseType));
    }

    public LicenseTypeResponse updateLicenseType(Long id, LicenseTypeUpdateDto licenseTypeUpdateDto) {
        LicenseType licenseType = licenseTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("LicenseType no found"));
        if (licenseTypeUpdateDto.getName() != null) {
            licenseType.setName(licenseTypeUpdateDto.getName());
        }
        if (licenseTypeUpdateDto.getDefaultDuration() != null) {
            licenseType.setDefaultDuration(licenseTypeUpdateDto.getDefaultDuration());
        }
        if (licenseTypeUpdateDto.getDescription() != null) {
            licenseType.setDescription(licenseTypeUpdateDto.getDescription());
        }
        return licenseTypeMapper.toLicenseTypeResponse(licenseTypeRepository.save(licenseType));

    }

    public void deleteLicenseType(Long id) {
        licenseTypeRepository.deleteById(id);
    }

    public List<LicenseTypeResponse> getAllLicenseTypes() {
        return licenseTypeRepository.findAll().stream()
                .map(licenseTypeMapper::toLicenseTypeResponse)
                .toList();
    }

}