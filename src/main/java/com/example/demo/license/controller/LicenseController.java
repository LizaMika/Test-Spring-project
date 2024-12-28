package com.example.demo.license.controller;

import com.example.demo.device.service.DeviceService;
import com.example.demo.license.domain.Ticket;
import com.example.demo.license.dto.*;
import com.example.demo.license.service.LicenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

//TODO: 1. В контроллерах не должны возвращаться сущности из базы данных
//TODO: 2. activateLicense - Для чего проводится проверка аутентификации внутри метода?

@RestController
@RequestMapping("/license")
@AllArgsConstructor
@Slf4j
@Validated
public class LicenseController {

    private final LicenseService licenseService;

    private final DeviceService deviceService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<LicenseResponse> add(@Valid @RequestBody LicenseRequest licenseRequest) throws NoSuchAlgorithmException {

        log.info("Creating license with description: {}", licenseRequest.getDescription());

        LicenseResponse licenseResponse = licenseService.addLicense(licenseRequest);

        return ResponseEntity.ok(licenseResponse);
    }

    @PostMapping("/activate")
    public ResponseEntity<Ticket> activateLicense(@Valid @RequestBody ActivationRequest activationRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Activating license with params: activationCode = {}, deviceName = {}, macAddress = {} by user = {}",
                activationRequest.getActivationCode(), activationRequest.getDeviceName(), activationRequest.getMacAddress(), authentication.getName());

        Ticket ticket = deviceService.activateLicenseOnDevice(activationRequest, authentication.getName());

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/info")
    public ResponseEntity<Ticket> getLicenseInfo(@Valid @RequestBody LicenseInfoRequest licenseInfoRequest) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String macAddress = licenseInfoRequest.getMacAddress();
        String code = licenseInfoRequest.getCode();

        log.info("Getting license info with params: login = {}, mac address = {}, code = {}", login, macAddress, code);

        Ticket ticket = licenseService.getLicenseInfo(macAddress, login, code);

        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/update")
    public ResponseEntity<Ticket> updateLicense(@Valid @RequestBody LicenseUpdateRequest updateRequest) throws Exception {
        Ticket ticket = licenseService.updateExistentLicense(updateRequest.getLicenseKey(), updateRequest.getLogin());
        return ResponseEntity.ok(ticket);

    }
}