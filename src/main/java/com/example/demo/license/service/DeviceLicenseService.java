package com.example.demo.license.service;

import com.example.demo.license.domain.DeviceLicense;
import com.example.demo.license.domain.License;
import com.example.demo.license.repository.DeviceLicenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceLicenseService {

    private final DeviceLicenseRepository deviceLicenseRepository;

    public List<DeviceLicense> getDeviceLicensesByLicense(License license) {
        return deviceLicenseRepository.getDeviceLicensesByLicense(license);
    }

    public void save(DeviceLicense deviceLicense) {
        deviceLicenseRepository.save(deviceLicense);
    }

    public Optional<DeviceLicense> getDeviceLicenseByDeviceIdAndLicenseId(Long deviceId, Long licenseId) {
        return deviceLicenseRepository.getDeviceLicenseByDeviceIdAndLicenseId(deviceId, licenseId);
    }
}
