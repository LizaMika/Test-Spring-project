package com.example.demo.license.repository;

import com.example.demo.licenseType.domain.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
}
