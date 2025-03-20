package com.sparta.companyservice.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.companyservice.application.dto.CompanyCreateDto;
import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import com.sparta.companyservice.infrastructure.client.HubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    long userId = 1L; // 실제로는 인증된 사용자 ID 가져와야 함

    @Transactional // 생성
    public CompanyDto createCompany(CompanyCreateDto dto) {
        validateHubExists(dto.hubId());
        Company company = Company.create(
                dto.name(),
                dto.address(),
                dto.hubId(),
                dto.type(),
                userId
        );
        Company createdCompany = companyRepository.save(company);
        return CompanyDto.fromEntity(createdCompany);
    }

    @Transactional(readOnly = true) // 전체 조회
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(CompanyDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true) // 단일 조회
    public CompanyDto getCompanyById(UUID id) {
        return CompanyDto.fromEntity(findCompany(id));
    }

    @Transactional // 수정
    public CompanyDto updateCompany(UUID id, CompanyUpdateDto dto) {
        Company company = findCompany(id);

        // hubId가 변경된 경우 유효한 허브Id인지 유효성 검사
        if (dto.hubId() != null && !dto.hubId().equals(company.getHubId())) {
            validateHubExists(dto.hubId());
        }

        company.applyUpdate(dto, userId);
        return CompanyDto.fromEntity(company);
    }

    @Transactional // 삭제
    public void deleteCompany(UUID id) {
        Company company = findCompany(id);
        company.delete(userId);
    }

    /// //////////////////////////////////////////////////////////////////////////////////

    private void validateHubExists(UUID hubId) {
        if (!hubClient.existsById(hubId)) {
            throw new ResourceNotFoundException("해당 허브가 존재하지 않습니다.");
        }
    }

    private Company findCompany(UUID id) {
        return companyRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResourceNotFoundException("해당 업체를 찾을 수 없습니다."));
    }
}