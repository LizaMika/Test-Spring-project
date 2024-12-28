package com.example.demo.license.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ticket {

    private Date currentDate;

    private Integer lifeTime;

    private Date activationDate;

    private Date expirationDate;

    private Long userId;

    private Long deviceId;

    private Boolean isBlocked;

    private String signature;
}
