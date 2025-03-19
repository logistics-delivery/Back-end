package com.sparta.companyservice.application.dto;

import com.sparta.companyservice.domain.model.CompanyType;

import java.util.UUID;

// Controller로부터 받은 요청 데이터를 내부 비즈니스 로직에서 사용할 수 있게 가공한 객체
public record CompanyCreateDto (
        String name,
        CompanyType type,
        UUID hubId,
        String address
) {}
