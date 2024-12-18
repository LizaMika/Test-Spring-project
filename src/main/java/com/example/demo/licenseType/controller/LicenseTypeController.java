package com.example.demo.licenseType.controller;

import com.example.demo.errors.NotFoundException;
import com.example.demo.licenseType.dto.LicenseTypeRequest;
import com.example.demo.licenseType.dto.LicenseTypeResponse;
import com.example.demo.licenseType.dto.LicenseTypeUpdateDto;
import com.example.demo.licenseType.mapper.LicenseTypeMapper;
import com.example.demo.licenseType.service.LicenseTypeService;
import com.example.demo.licenseType.domain.LicenseType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license-types")
@AllArgsConstructor
@Validated
public class LicenseTypeController {

    private final LicenseTypeService licenseTypeService;
    private final LicenseTypeMapper licenseTypeMapper;

    @PostMapping
    public ResponseEntity<LicenseTypeResponse> createLicenseType(@Valid @RequestBody LicenseTypeRequest licenseTypeRequest) {
        LicenseTypeResponse licenseType = licenseTypeService.createLicenseType(licenseTypeRequest);
        return ResponseEntity.ok(licenseType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseTypeResponse> getLicenseType(@PathVariable Long id) {
        LicenseTypeResponse licenseType = licenseTypeMapper.toLicenseTypeResponse(licenseTypeService.getLicenseTypeById(id));
        return ResponseEntity.ok(licenseType);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LicenseTypeResponse> updateLicenseType(@PathVariable Long id, @Valid @RequestBody LicenseTypeUpdateDto licenseTypeUpdateDto) {
        LicenseTypeResponse licenseType = licenseTypeService.updateLicenseType(id, licenseTypeUpdateDto);
        return ResponseEntity.ok(licenseType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLicenseType(@PathVariable Long id) {
        licenseTypeService.deleteLicenseType(id);
    }

    @GetMapping
    public ResponseEntity<List<LicenseTypeResponse>> getAllLicenseTypes() {
        List<LicenseTypeResponse> licenseTypes = licenseTypeService.getAllLicenseTypes();
        return ResponseEntity.ok(licenseTypes);
    }
}