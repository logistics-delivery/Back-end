package com.sparta.companyservice.domain.repository;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);

    Optional<Company> findByIdAndDeletedAtIsNull(UUID id);

    Page<Company> searchCompanies(String name, String address, CompanyType type, Pageable pageable);

    boolean existsByIdAndDeletedAtIsNull(UUID id);
}