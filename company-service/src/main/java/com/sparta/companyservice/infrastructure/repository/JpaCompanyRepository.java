package com.sparta.companyservice.infrastructure.repository;

import com.sparta.companyservice.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {
}
