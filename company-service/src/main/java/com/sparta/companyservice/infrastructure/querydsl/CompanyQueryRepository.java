package com.sparta.companyservice.infrastructure.querydsl;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQueryRepository {
    Page<Company> searchCompanies(String name, String address, CompanyType companyType, Pageable pageable);
}
