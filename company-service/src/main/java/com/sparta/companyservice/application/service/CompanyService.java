package com.sparta.companyservice.application.service;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.service.CompanyDomainService;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDomainService companyDomainService;

    public CompanyDto createCompany(CompanyCreateRequest request) {
        long userId = 1L; // 실제로는 인증된 사용자 ID 가져와야 함
        Company company = companyDomainService.create(
                request.name(),
                request.type(),
                request.hub_id(),
                request.address(),
                userId
        );
        return CompanyDto.fromEntity(company);
    }
}