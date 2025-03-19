package com.sparta.companyservice.presentation.controller;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.application.service.CompanyService;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import com.sparta.companyservice.presentation.request.CompanyUpdateRequest;
import com.sparta.companyservice.presentation.response.CompanyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 생성
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyCreateRequest request) {
        CompanyDto createdCompany = companyService.createCompany(request.toDto());
        return ResponseEntity.ok(CompanyResponse.fromDto(createdCompany));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        List<CompanyResponse> responses = companies.stream().map(CompanyResponse::fromDto).toList();
        return ResponseEntity.ok(responses);
    }

    // 단일 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable UUID companyId) {
        CompanyDto oneCompany = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(CompanyResponse.fromDto(oneCompany));
    }

    // 수정
    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable UUID companyId, @RequestBody CompanyUpdateRequest request) {
        CompanyDto updated = companyService.updateCompany(companyId, request);
        return ResponseEntity.ok(CompanyResponse.fromDto(updated));
    }

    // 삭제

}

