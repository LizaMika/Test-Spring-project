package com.example.demo.device.service;

import com.example.demo.device.domain.Device;
import com.example.demo.device.dto.DeviceRequest;
import com.example.demo.device.dto.DeviceResponse;
import com.example.demo.device.dto.DeviceUpdateDto;
import com.example.demo.device.mapper.DeviceMapper;
import com.example.demo.device.repository.DeviceRepository;
import com.example.demo.errors.EntityAlreadyExistsException;
import com.example.demo.errors.NotFoundException;
import com.example.demo.license.domain.License;
import com.example.demo.license.domain.Ticket;
import com.example.demo.license.dto.ActivationRequest;
import com.example.demo.license.service.LicenseService;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: 1. registerDevice - почему происходит активация после добавления устройства?
//TODO: 2. getLicenseInfo - почему в сервисе устройств, а не лицензии?

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final LicenseService licenseService;
    private final UserService userService;
    private final DeviceMapper deviceMapper;

    public Ticket registerDevice(ActivationRequest activationRequest, String userName) {
        User user = userService.findUserByLogin(userName);
        Device device = deviceRepository.getDeviceByMacAddress(activationRequest.getMacAddress());
        if (device == null) {
            device = Device.builder()
                    .name(activationRequest.getDeviceName())
                    .macAddress(activationRequest.getMacAddress())
                    .user(user)
                    .build();
        } else if (!device.getUser().equals(user)) {
            throw new EntityAlreadyExistsException("Device already registered by another user");
        }

        deviceRepository.save(device);

        String activationCode = activationRequest.getActivationCode();
        String login = user.getLogin();
        Ticket ticket = licenseService.activateLicense(activationCode, device, login);
        log.info("Activated license " + ticket);
        return ticket;
    }

    public Ticket getLicenseInfo(String macAddress, String login, String code) {
        User user = userService.findUserByLogin(login);

        Device device = deviceRepository.findDeviceByMacAddressAndUser(macAddress, user);
        if (device == null) {
            throw new NotFoundException("Device not found");
        }

        License activeLicense = licenseService.getActiveLicenseForDevice(device, login, code);

        return licenseService.generateTicket(activeLicense, device);
    }


    public DeviceResponse createDevice(DeviceRequest deviceRequest) {
        User user = userRepository.findById(deviceRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceMapper.toDevice(deviceRequest, user);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toDeviceResponse(savedDevice);
    }

    public DeviceResponse getDeviceById(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Device not found"));
        return deviceMapper.toDeviceResponse(device);
    }

    public DeviceResponse updateDevice(Long id, DeviceUpdateDto deviceUpdateDto) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device not found"));
        if (deviceUpdateDto.getDeviceName() != null) {
            device.setName(deviceUpdateDto.getDeviceName());
        }
        if (deviceUpdateDto.getMacAddress() != null) {
            device.setMacAddress(deviceUpdateDto.getMacAddress());
        }
        if (deviceUpdateDto.getUserId() != null) {
            User user = userRepository.findById(deviceUpdateDto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            device.setUser(user);
        }
        Device updatedDevice = deviceRepository.save(device);

        return deviceMapper.toDeviceResponse(updatedDevice);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public List<DeviceResponse> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

}