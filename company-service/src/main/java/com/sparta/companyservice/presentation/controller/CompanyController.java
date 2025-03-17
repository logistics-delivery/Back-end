package com.sparta.companyservice.presentation.controller;

import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.presentation.request.CompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyRequest request) {
        CompanyDto created = companyService.createCompany(request);
        return ResponseEntity.ok(created);
    }
}

