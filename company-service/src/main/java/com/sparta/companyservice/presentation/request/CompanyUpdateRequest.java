package com.sparta.companyservice.presentation.request;

import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import com.sparta.companyservice.domain.model.CompanyType;

import java.util.UUID;

public record CompanyUpdateRequest (
        String name,
        CompanyType type,
        UUID hubId,
        String address
    ) {
    public CompanyUpdateDto toDto() {
        return new CompanyUpdateDto(name, type, hubId, address);
    }
}
