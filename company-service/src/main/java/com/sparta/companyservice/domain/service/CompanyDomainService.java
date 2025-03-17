package com.sparta.companyservice.domain.service;

import com.sparta.companyservice.domain.model.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyDomainService {

    public void validate(Company company) {
        if (company.getName().length() < 2) {
            throw new IllegalArgumentException("업체명은 2자 이상이어야 합니다.");
        }
    }
}
