package com.example.demo.licenseType.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "license_types")
public class LicenseType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "default_duration")
    private Integer defaultDuration;

    @Column(name = "description")
    private String description;
}
