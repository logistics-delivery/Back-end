package com.sparta.companyservice.presentation.request;

import com.sparta.companyservice.domain.model.CompanyType;

import java.util.UUID;

public record CompanyUpdateRequest (
        String name,
        String address,
        UUID hub_id,
        CompanyType type
    ) {}
