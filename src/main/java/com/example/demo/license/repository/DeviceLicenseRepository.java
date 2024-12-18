package com.example.demo.license.repository;

import com.example.demo.license.domain.DeviceLicense;
import com.example.demo.license.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    Optional<DeviceLicense> getDeviceLicenseByDeviceIdAndLicenseId(Long deviceId, Long licenseId);
    List<DeviceLicense> getDeviceLicensesByLicense(License license);

}
