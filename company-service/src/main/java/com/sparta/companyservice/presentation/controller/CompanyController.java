package com.sparta.companyservice.presentation.controller;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.application.service.CompanyService;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import com.sparta.companyservice.presentation.request.CompanyUpdateRequest;
import com.sparta.companyservice.presentation.response.CompanyDeleteResponse;
import com.sparta.companyservice.presentation.response.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Company Service", description = "업체 서비스 API")
public class CompanyController {

    private final CompanyService companyService;

    // 생성
    @Operation(summary = "Company 등록", description = "Company 등록 API")
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyCreateRequest request) {
        CompanyDto createdCompany = companyService.createCompany(request.toDto());
        return ResponseEntity.ok(CompanyResponse.fromDto(createdCompany));
    }

    // 전체 조회
    @Operation(summary = "Company 전체 조회", description = "Company 전체 조회 API")
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        List<CompanyResponse> responses = companies.stream().map(CompanyResponse::fromDto).toList();
        return ResponseEntity.ok(responses);
    }

    // 단일 조회
    @Operation(summary = "Company 단건 조회", description = "Company 단건 조회 API")
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable UUID companyId) {
        CompanyDto oneCompany = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(CompanyResponse.fromDto(oneCompany));
    }

    // 수정
    @Operation(summary = "Company 수정", description = "Company 수정 API")
    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable UUID companyId, @Valid @RequestBody CompanyUpdateRequest request) {
        CompanyDto updatedCompany = companyService.updateCompany(companyId, request.toDto());
        return ResponseEntity.ok(CompanyResponse.fromDto(updatedCompany));
    }

    // 삭제
    @Operation(summary = "Company 삭제", description = "Company 삭제 API")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<CompanyDeleteResponse> deleteCompany(@PathVariable UUID companyId) {
        CompanyDeleteResponse deletedCompany = companyService.deleteCompany(companyId);
        return ResponseEntity.ok(deletedCompany);
    }

}

