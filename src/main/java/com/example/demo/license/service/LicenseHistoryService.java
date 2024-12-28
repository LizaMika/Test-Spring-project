package com.example.demo.license.service;

import com.example.demo.license.domain.LicenseHistory;
import com.example.demo.license.dto.LicenseHistoryResponse;
import com.example.demo.license.mapper.LicenseMapper;
import com.example.demo.license.repository.LicenseHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//TODO: 1. Читать историю нельзя?

@Service
@RequiredArgsConstructor
public class LicenseHistoryService {

    private final LicenseHistoryRepository licenseHistoryRepository;

    private final LicenseMapper licenseMapper;

    public void saveLicenseHistory(LicenseHistory licenseHistory) {
        licenseHistoryRepository.save(licenseHistory);
    }

    @Transactional
    public LicenseHistoryResponse getLicenseHistoryByLicenseId(Long licenseId) {
        return licenseMapper.toLicenseHistoryResponse(licenseHistoryRepository.findByLicenseId(licenseId));
    }
}
