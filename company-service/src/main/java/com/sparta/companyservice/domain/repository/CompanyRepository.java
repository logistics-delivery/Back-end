package com.sparta.companyservice.domain.repository;

import com.sparta.companyservice.domain.model.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);

    List<Company> findAll();

    Optional<Company> findById(UUID id);

    boolean existsByName(String name);

    void delete(Company company);
}