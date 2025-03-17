package com.sparta.companyservice.application.service;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import com.sparta.companyservice.domain.service.CompanyDomainService;
import com.sparta.companyservice.presentation.request.CompanyRequest;
import com.sparta.companyservice.infrastructure.client.HubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final HubClient hubClient;
    private final CompanyRepository companyRepository;
    private final CompanyDomainService domainService;

    public CompanyDto createCompany(CompanyRequest request) {
        if (!hubClient.existsById(request.getHubId())) {
            throw new IllegalArgumentException("유효하지 않은 허브 ID입니다.");
        }

        Company company = Company.create(
                UUID.randomUUID(),
                request.getName(),
                CompanyType.fromKor(request.getType()),
                request.getHubId(),
                request.getAddress()
        );

        domainService.validate(company);
        Company saved = companyRepository.save(company);

        return CompanyDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .type(saved.getType().getKor())
                .hubId(saved.getHubId())
                .address(saved.getAddress())
                .createdAt(saved.getCreatedAt())
                .createdBy("admin_user") // 임시로 해둠
                .build();
    }
}
