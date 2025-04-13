package com.sparta.companyservice.presentation.controller;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.application.service.CompanyService;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import com.sparta.companyservice.presentation.request.CompanyUpdateRequest;
import com.sparta.companyservice.presentation.response.CompanyDeleteResponse;
import com.sparta.companyservice.presentation.response.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Company Service", description = "Company Service API")
public class CompanyController {

    private final CompanyService companyService;

    // 업체 존재 확인
    @GetMapping("/{id}/exists")
    public boolean existsById(@PathVariable("id") UUID id) {
        return companyService.existsById(id);
    }

    // 생성
    @Operation(summary = "Company 등록", description = "Company 생성 api 입니다.")
    @RoleCheck("ROLE_COMPANY")
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyCreateRequest request, @RequestHeader("user_id") Long userId) {
        CompanyDto createdCompany = companyService.createCompany(request.toDto(), userId);
        return ResponseEntity.ok(CompanyResponse.fromDto(createdCompany));
    }

    // 전체 목록 조회 & 검색
    @Operation(summary = "Company 조회", description = "Company 조회 api 입니다.")
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> searchCompanies(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "type", required = false) CompanyType type,
            Pageable pageable
    ) {
        Pageable validatedPageable = validatePageSize(pageable); // 페이지 사이즈 검증

        Page<CompanyDto> companies = companyService.searchCompanies(name, address, type, validatedPageable);
        Page<CompanyResponse> responses = companies.map(CompanyResponse::fromDto);
        return ResponseEntity.ok(responses);
    }

    // 단일 조회
    @Operation(summary = "Company 조회", description = "Company 단건 조회 api 입니다.")
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable("companyId") UUID companyId
    ) {
        CompanyDto oneCompany = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(CompanyResponse.fromDto(oneCompany));
    }

    // 수정
    @Operation(summary = "Company 수정", description = "Company 수정 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable("companyId") UUID companyId, @Valid @RequestBody CompanyUpdateRequest request, @RequestHeader("user_id") Long userId) {
        CompanyDto updatedCompany = companyService.updateCompany(companyId, request.toDto(), userId);
        return ResponseEntity.ok(CompanyResponse.fromDto(updatedCompany));
    }

    // 삭제
    @Operation(summary = "Company 삭제", description = "Company 삭제 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<CompanyDeleteResponse> deleteCompany(@PathVariable("companyId") UUID companyId, @RequestHeader("user_id") Long userId) {
        CompanyDeleteResponse deletedCompany = companyService.deleteCompany(companyId, userId);
        return ResponseEntity.ok(deletedCompany);
    }

    private Pageable validatePageSize(Pageable pageable) { // 페이지 사이즈 검증
        List<Integer> allowedSizes = List.of(10, 30, 50);
        int size = allowedSizes.contains(pageable.getPageSize()) ? pageable.getPageSize() : 10;
        return PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
    }

}

