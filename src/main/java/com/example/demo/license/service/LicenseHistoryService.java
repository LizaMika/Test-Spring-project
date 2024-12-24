package com.example.demo.license.service;

import com.example.demo.license.domain.LicenseHistory;
import com.example.demo.license.repository.LicenseHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: 1. Читать историю нельзя?

@Service
@AllArgsConstructor
public class LicenseHistoryService {

    private final LicenseHistoryRepository licenseHistoryRepository;

    public void saveLicenseHistory(LicenseHistory licenseHistory) {
        licenseHistoryRepository.save(licenseHistory);
    }

}
