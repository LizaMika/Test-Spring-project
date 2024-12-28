package com.example.demo.license.service;

import com.example.demo.device.domain.Device;
import com.example.demo.device.repository.DeviceRepository;
import com.example.demo.errors.exceptions.ConflictException;
import com.example.demo.errors.exceptions.NotFoundException;
import com.example.demo.license.domain.DeviceLicense;
import com.example.demo.license.domain.License;
import com.example.demo.license.domain.LicenseHistory;
import com.example.demo.license.dto.LicenseResponse;
import com.example.demo.license.mapper.LicenseMapper;
import com.example.demo.licenseType.domain.LicenseType;
import com.example.demo.license.dto.LicenseRequest;
import com.example.demo.license.repository.LicenseRepository;
import com.example.demo.license.domain.Ticket;
import com.example.demo.licenseType.service.LicenseTypeService;
import com.example.demo.product.domain.Product;
import com.example.demo.product.service.ProductService;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

//TODO: 1. generateSignature - отсутствует реализация цифровой подписи
//TODO: 2. addLicense - user должен устанавливаться при первой активации
//TODO: 3. validateActivation - лицензия может быть повторно активирована на другом устройстве
//TODO: 4. activateLicense - у вас при каждой активации устанавливаются даты. Нужно только при первой
//TODO: 5. generateTicket - слишком большое время жизни тикета
//TODO: 6. generateTicket - неверно устанавливается идентификатор пользователя
//TODO: 7. generateTicket - неверно устанавливается информация о блокировке лицензии

@Service
@AllArgsConstructor
@Slf4j
public class LicenseService {

    private final LicenseRepository licenseRepository;

    private final ProductService productService;

    private final UserService userService;

    private final LicenseTypeService licenseTypeService;

    private final LicenseHistoryService licenseHistoryService;

    private final DeviceLicenseService deviceLicenseService;

    private final DeviceRepository deviceRepository;

    private final PasswordEncoder passwordEncoder;

    private final LicenseMapper licenseMapper;

    private static final BigInteger MODULUS = new BigInteger("31").multiply(new BigInteger("23"));

    private static final BigInteger PUBLIC_EXPONENT = new BigInteger("283");
    private final UserRepository userRepository;

    public LicenseResponse addLicense(LicenseRequest licenseRequest) throws NoSuchAlgorithmException {

        Product product = productService.getProductById(licenseRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        User owner = userRepository.findById(licenseRequest.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        LicenseType licenseType = licenseTypeService.getLicenseTypeById(licenseRequest.getLicenseTypeId());

        String code = generateLicenseCode(licenseRequest);

        License license = License.builder()
                .code(code)
                .product(product)
                .type(licenseType)
                .firstActivationDate(null)
                .endingDate(null)
                .blocked(false)
                .deviceCount(licenseRequest.getDeviceCount())
                .owner(owner)
                .duration(licenseRequest.getDuration())
                .description(licenseRequest.getDescription())
                .product(product)
                .build();

        licenseRepository.save(license);

        LicenseHistory licenseHistory = LicenseHistory.builder()
                .license(license)
                .status("CREATED")
                .description("Created license")
                .build();

        licenseHistoryService.saveLicenseHistory(licenseHistory);

        return licenseMapper.toLicenseResponse(license);
    }

    public Ticket getLicenseInfo(String macAddress, String login, String code) throws Exception {
        User user = userService.findUserByLogin(login);

        Device device = deviceRepository.findDeviceByMacAddressAndUser(macAddress, user);
        if (device == null) {
            throw new NotFoundException("Device not found");
        }

        License activeLicense = getActiveLicenseForDevice(device, login, code);

        return generateTicket(activeLicense, device);
    }

    public Ticket activateLicense(String activationCode, Device device, String login) throws Exception {
        License license = licenseRepository.getLicensesByCode(activationCode).orElseThrow(() -> new NotFoundException("License not found"));

        User user = userService.findUserByLogin(login);

        validateActivation(license);

        if (license.getFirstActivationDate() == null) {
            license.setFirstActivationDate(new Date());
        }

        createDeviceLicense(license, device);

        updateLicenseForActivation(license, user);

        LicenseHistory licenseHistory = LicenseHistory.builder()
                .license(license)
                .user(license.getOwner())
                .status("ACTIVATED")
                .changeDate(new Date())
                .description("License activated")
                .build();

        licenseHistoryService.saveLicenseHistory(licenseHistory);

        return generateTicket(license, device);
    }

    public License getActiveLicenseForDevice(Device device, String login, String code) {

        userService.findUserByLogin(login);

        License license = licenseRepository.getLicensesByCode(code).orElseThrow(() -> new NotFoundException("License not found"));
        if (license.getBlocked()) {
            throw new ConflictException("License is blocked");
        }
        deviceLicenseService.getDeviceLicenseByDeviceIdAndLicenseId(device.getId(), license.getId())
                .orElseThrow(() -> new NotFoundException("Device license not found"));

        return license;
    }

    public Ticket updateExistentLicense(String licenseCode, String login) throws Exception {
        License license = licenseRepository.getLicensesByCode(licenseCode).orElseThrow(() -> new NotFoundException("License not found"));
        if (license.getBlocked()) {
            throw new ConflictException("License is blocked");
        }

        license.setEndingDate(new Date(System.currentTimeMillis() + license.getDuration()));
        licenseRepository.save(license);

        LicenseHistory licenseHistory = LicenseHistory.builder()
                .license(license)
                .user(license.getOwner())
                .status("UPDATED")
                .changeDate(new Date())
                .description("License updated")
                .build();
        licenseHistoryService.saveLicenseHistory(licenseHistory);

        return generateTicket(license, deviceRepository.findDeviceByUser(userService.findUserByLogin(login)));
    }

    private Ticket generateTicket(License license, Device device) throws Exception {
        Ticket ticket = new Ticket();

        ticket.setCurrentDate(new Date());
        ticket.setLifeTime(license.getDuration());
        ticket.setActivationDate(new Date(license.getFirstActivationDate().getTime()));
        ticket.setExpirationDate(new Date(license.getEndingDate().getTime()));
        if (license.getUser() != null) {
            ticket.setUserId(license.getUser().getId());
        }
        ticket.setDeviceId(device.getId());
        ticket.setIsBlocked(license.getBlocked());
        log.info("ticket " + ticket.toString());
        ticket.setSignature(generateSignature(ticket));
        log.info("ticket " + ticket.toString());
        return ticket;
    }

    private void validateActivation(License license) {

        if (license.getBlocked()) {
            throw new ConflictException("License is blocked");
        }

        if (license.getEndingDate() != null) {
            if (license.getEndingDate().before(new Date())) {
                throw new ConflictException("License is expired");
            }
        }

        if (license.getDeviceCount() <= deviceLicenseService.getDeviceLicensesByLicense(license).size()) {
            throw new ConflictException("Device count exceeded");
        }
    }

    private void createDeviceLicense(License license, Device device) {
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivationDate(new Date());

        deviceLicenseService.save(deviceLicense);
    }

    private void updateLicenseForActivation(License license, User user) {
        license.setEndingDate(new Date(System.currentTimeMillis() + license.getDuration()));
        license.setUser(user);
        licenseRepository.save(license);
    }

    private String generateSignature(Ticket ticket) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(ticket.toString().getBytes());
        BigInteger hash = new BigInteger(1, hashBytes);

        BigInteger privateExponent = new BigInteger("7");

        BigInteger signature = hash.pow(privateExponent.intValue()).mod(MODULUS);

        return Base64.getEncoder().encodeToString(signature.toByteArray());
    }

    private String generateLicenseCode(LicenseRequest licenseRequest) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String data = licenseRequest.getProductId() + licenseRequest.getOwnerId() + licenseRequest.getLicenseTypeId() + licenseRequest.getDeviceCount() + licenseRequest.getDuration() + licenseRequest.getDescription() + LocalDateTime.now();
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
