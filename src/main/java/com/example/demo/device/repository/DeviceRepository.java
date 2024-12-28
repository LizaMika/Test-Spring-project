package com.example.demo.device.repository;

import com.example.demo.device.domain.Device;
import com.example.demo.license.domain.License;
import com.example.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> getDeviceByMacAddress(String macAddress);
    Device findDeviceByMacAddressAndUser(String macAddress, User user);
    Device findDeviceByMacAddress(String macAddress);
    Device findDeviceByUser(UserDetails user);
    Optional<List<Device>> findAllByUserId(Long userId);
}