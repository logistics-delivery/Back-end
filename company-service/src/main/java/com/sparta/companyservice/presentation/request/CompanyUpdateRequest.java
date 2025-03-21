package com.sparta.companyservice.presentation.request;

import com.sparta.companyservice.application.dto.CompanyUpdateDto;

import java.util.UUID;

public record CompanyUpdateRequest (
        String name,
        UUID hubId,
        String address
    ) {
    public CompanyUpdateDto toDto() {
        return new CompanyUpdateDto(name, hubId, address);
    }
}
