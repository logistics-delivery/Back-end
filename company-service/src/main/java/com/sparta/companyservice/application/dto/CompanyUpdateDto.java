package com.sparta.companyservice.application.dto;

import com.sparta.companyservice.domain.model.CompanyType;

import java.util.UUID;

public record CompanyUpdateDto(
        String name,
        UUID hubId,
        String address
) {}
