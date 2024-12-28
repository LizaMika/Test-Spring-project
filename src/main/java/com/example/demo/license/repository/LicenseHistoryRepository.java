package com.example.demo.license.repository;

import com.example.demo.license.domain.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
    LicenseHistory findByLicenseId(Long licenseId);
}
