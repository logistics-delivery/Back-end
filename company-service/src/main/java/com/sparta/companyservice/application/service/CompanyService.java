package com.sparta.companyservice.application.service;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.service.CompanyDomainService;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        // presentation에서 받은 요청을 파싱해서 domainService에 넘기고 처리 결과를 DTO로 만들어서 다시 controller로 반환
        return CompanyDto.fromEntity(company);
    }

    public List<CompanyDto> getAllCompanies() {
        return companyDomainService.findAll()
                .stream()
                .map(CompanyDto::fromEntity)
                .toList();
    }

    public CompanyDto getCompanyById(UUID id) {
        Company company = companyDomainService.findById(id);
        return CompanyDto.fromEntity(company);
    }
}