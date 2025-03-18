package com.sparta.companyservice.application.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CompanyDto {
    private UUID id;
    private String name;
    private String type;
    private UUID hubId;
    private String address;
    private LocalDateTime createdAt;
    private String createdBy;
}

