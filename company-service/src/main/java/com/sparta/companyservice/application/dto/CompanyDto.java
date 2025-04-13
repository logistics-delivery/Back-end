package com.sparta.companyservice.application.dto;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;

import java.time.LocalDateTime;
import java.util.UUID;

// 서비스 <-> 도메인 간 내부 데이터 전달용 DTO

public record CompanyDto(
        UUID id,
        String name,
        CompanyType type,
        UUID hubId,
        String address,
        LocalDateTime createdAt,
        long createdBy
) {
    public static CompanyDto fromEntity(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getType(),
                company.getHubId(),
                company.getAddress(),
                company.getCreatedAt(),
                company.getCreatedBy()
        );
    }
}

