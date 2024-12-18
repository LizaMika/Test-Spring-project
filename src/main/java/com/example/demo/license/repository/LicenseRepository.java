package com.example.demo.license.repository;

import com.example.demo.license.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> getLicensesByCode(String code);
    Optional<List<License>> findAllByUserId(Long userId);
}
