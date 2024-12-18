package com.example.demo.device.controller;

import com.example.demo.device.dto.DeviceRequest;
import com.example.demo.device.dto.DeviceResponse;
import com.example.demo.device.dto.DeviceUpdateDto;
import com.example.demo.device.service.DeviceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@AllArgsConstructor
@Validated
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest deviceRequest) {
        log.info("Creating device : {}", deviceRequest);
        DeviceResponse device = deviceService.createDevice(deviceRequest);
        log.info("Created device : {}", device);
        return ResponseEntity.ok(device);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Long id) {
        log.info("Getting device : {}", id);
        DeviceResponse device = deviceService.getDeviceById(id);
        log.info("Got device : {}", device);
        return ResponseEntity.ok(device);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceUpdateDto deviceUpdateDto) {
        log.info("Updating device : {}", deviceUpdateDto);
        DeviceResponse device = deviceService.updateDevice(id, deviceUpdateDto);
        log.info("Updated device : {}", device);
        return ResponseEntity.ok(device);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Long id) {
        log.info("Deleting device : {}", id);
        deviceService.deleteDevice(id);
        log.info("Deleted device : {}", id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        log.info("Getting all devices");
        List<DeviceResponse> devices = deviceService.getAllDevices();
        log.info("Got all devices : {}", devices);
        return ResponseEntity.ok(devices);
    }
}