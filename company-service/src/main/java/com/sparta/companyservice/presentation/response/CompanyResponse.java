package com.sparta.companyservice.presentation.response;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.domain.model.CompanyType;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        CompanyType type,
        UUID hub_id,
        String address,
        LocalDateTime created_at,
        long created_by
) {
    public static CompanyResponse fromDto(CompanyDto dto) {
        return new CompanyResponse(
                dto.id(),
                dto.name(),
                dto.type(),
                dto.hubId(),
                dto.address(),
                dto.createdAt(),
                dto.createdBy()
        );
    }
}
