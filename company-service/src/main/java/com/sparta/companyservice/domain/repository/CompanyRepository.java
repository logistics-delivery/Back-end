package com.sparta.companyservice.domain.repository;

import com.sparta.companyservice.domain.model.Company;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);
}