package com.example.demo.user.service;

import com.example.demo.device.domain.Device;
import com.example.demo.device.repository.DeviceRepository;
import com.example.demo.license.domain.License;
import com.example.demo.license.repository.LicenseRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LicenseRepository licenseRepository;
    private final DeviceRepository deviceRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username);
    }

    public UserResponse saveUser(User user){
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public User findUserByLogin(String login){
        return userRepository.findUserByLogin(login);
    }

    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Transactional
    public void deleteUser(Long id){
        Optional<List<License>> licenseList = licenseRepository.findAllByUserId(id);
        licenseList.ifPresent(licenses -> licenses.stream()
                .peek(license -> license.setUser(null))
                .map(licenseRepository::save));
        Optional<List<Device>> deviceList = deviceRepository.findAllByUserId(id);
        deviceList.ifPresent(devices -> devices.stream()
                .peek(device -> device.setUser(null))
                .map(deviceRepository::save));
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}