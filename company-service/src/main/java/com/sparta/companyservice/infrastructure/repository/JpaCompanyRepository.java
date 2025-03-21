package com.sparta.companyservice.infrastructure.repository;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import com.sparta.companyservice.infrastructure.querydsl.CompanyQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, UUID>, CompanyRepository, CompanyQueryRepository {
}