package com.example.demo.license.controller;

import com.example.demo.license.dto.*;
import com.example.demo.license.service.LicenseHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/license/history")
@RequiredArgsConstructor
@Slf4j
@Validated
public class LicenseHistoryController {

    private final LicenseHistoryService licenseHistoryService;

    @GetMapping("{id}")
    public ResponseEntity<LicenseHistoryResponse> getLicenseHistoryByLicenseId(@PathVariable Long id) {

        log.info("Getting license history by id = {}", id);

        LicenseHistoryResponse licenseHistoryResponse = licenseHistoryService.getLicenseHistoryByLicenseId(id);

        log.info("Got license history {}", licenseHistoryResponse);

        return ResponseEntity.ok(licenseHistoryResponse);
    }
}
