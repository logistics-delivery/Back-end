package com.sparta.companyservice.presentation.controller;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.presentation.request.CompanyCreateRequest;
import com.sparta.companyservice.application.service.CompanyService;
import com.sparta.companyservice.presentation.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyCreateRequest request) {
        CompanyDto created = companyService.createCompany(request);
        return ResponseEntity.ok(CompanyResponse.fromDto(created));
    }
}

