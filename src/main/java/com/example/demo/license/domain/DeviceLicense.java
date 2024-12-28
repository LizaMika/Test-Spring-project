package com.example.demo.license.domain;

import com.example.demo.device.domain.Device;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "device_licenses")
public class DeviceLicense {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id", referencedColumnName = "id")
    private License license;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    @Column(name = "activation_date")
    private Date activationDate;
}
