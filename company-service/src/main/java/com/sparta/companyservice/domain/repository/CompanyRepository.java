package com.sparta.companyservice.domain.repository;

import com.sparta.companyservice.domain.model.Company;

public interface CompanyRepository {
    Company save(Company company);
}