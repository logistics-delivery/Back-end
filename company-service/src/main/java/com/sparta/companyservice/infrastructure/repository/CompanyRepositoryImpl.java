package com.sparta.companyservice.infrastructure.repository;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JpaCompanyRepository jpaCompanyRepository;

    @Override
    public Company save(Company company) {
        return jpaCompanyRepository.save(company);
    }

    @Override
    public Optional<Company> findByIdAndDeletedAtIsNull(UUID id) {
        return jpaCompanyRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null);
    }

    @Override
    public Page<Company> searchCompanies(String name, String address, CompanyType type, Pageable pageable) {
        return jpaCompanyRepository.searchCompanies(name, address, type, pageable);
    }

    @Override
    public boolean existsByIdAndDeletedAtIsNull(UUID id) {
        return jpaCompanyRepository.existsByIdAndDeletedAtIsNull(id);
    }


}

