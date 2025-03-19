package com.sparta.companyservice.domain.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import com.sparta.companyservice.infrastructure.client.HubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// 도메인 계층: 오직 순수한 값만 받아서 도메인 로직을 수행해야 함 (순수 비즈니스 규칙 실행, 객체 생성/검증/저장 책임)

@Service
@RequiredArgsConstructor
public class CompanyDomainService {
    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    public Company create(String name, CompanyType type, UUID hubId, String address, long userId) {
        if (!hubClient.existsById(hubId)) {
            throw new ResourceNotFoundException("해당 허브가 존재하지 않습니다. 허브 ID: " + hubId);
        }

        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name(name)
                .type(type)
                .hubId(hubId)
                .address(address)
                .userId(userId)
                .build();
        return companyRepository.save(company);
    }


    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 업체를 찾을 수 없습니다."));
    }
}
