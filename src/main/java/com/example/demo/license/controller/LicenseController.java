package com.example.demo.license.controller;

import com.example.demo.device.service.DeviceService;
import com.example.demo.errors.UnauthorizedException;
import com.example.demo.license.domain.License;
import com.example.demo.license.domain.Ticket;
import com.example.demo.license.dto.ActivationRequest;
import com.example.demo.license.dto.LicenseInfoRequest;
import com.example.demo.license.dto.LicenseRequest;
import com.example.demo.license.dto.LicenseUpdateRequest;
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
    public ResponseEntity<License> add(@Valid @RequestBody LicenseRequest licenseRequest) throws NoSuchAlgorithmException {

        log.info("Creating license with description: {}", licenseRequest.getDescription());

        License license = licenseService.addLicense(licenseRequest);

        return ResponseEntity.ok(license);
    }

    @PostMapping("/activate")
    public ResponseEntity<Ticket> activateLicense(@Valid @RequestBody ActivationRequest activationRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not logged in yet");
        }
        log.info("Activating license with params: activationCode = {}, deviceName = {}, macAddress = {} by user = {}",
                activationRequest.getActivationCode(), activationRequest.getDeviceName(), activationRequest.getMacAddress(), authentication.getName());

        Ticket ticket = deviceService.registerDevice(activationRequest, authentication.getName());

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/info")
    public ResponseEntity<Ticket> getLicenseInfo(@Valid @RequestBody LicenseInfoRequest licenseInfoRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String macAddress = licenseInfoRequest.getMacAddress();
        String code = licenseInfoRequest.getCode();

        log.info("Getting license info with params: login = {}, mac address = {}, code = {}", login, macAddress, code);

        Ticket ticket = deviceService.getLicenseInfo(macAddress, login, code);

        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/update")
    public ResponseEntity<Ticket> updateLicense(@Valid @RequestBody LicenseUpdateRequest updateRequest) {
        Ticket ticket = licenseService.updateExistentLicense(updateRequest.getLicenseKey(), updateRequest.getLogin());
        return ResponseEntity.ok(ticket);

    }
}