package com.sparta.companyservice;

import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.infrastructure.querydsl.CompanyQueryRepositoryImpl;
import com.sparta.companyservice.infrastructure.repository.JpaCompanyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(CompanyQueryRepositoryImpl.class)
public class CompanyQueryRepositoryImplTest {

    @Autowired
    private CompanyQueryRepositoryImpl companyQueryRepository;

    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    @PersistenceContext
    private EntityManager em;

    private final UUID hubId = UUID.randomUUID();

    private Company c1, c2, c3, c4;

    @BeforeEach
    public void setUp() throws InterruptedException {
        long userId = 1L;

        c1 = Company.create("Alpha", "Seoul", hubId, CompanyType.PRODUCER, userId);
        Thread.sleep(10);
        c2 = Company.create("Beta", "Busan", hubId, CompanyType.RECEIVER, userId);
        Thread.sleep(10);
        c3 = Company.create("Gamma", "Seoul", hubId, CompanyType.PRODUCER, userId);
        Thread.sleep(10);
        c4 = Company.create("Delta", "Jeju", hubId, CompanyType.PRODUCER, userId);

        jpaCompanyRepository.save(c1);
        jpaCompanyRepository.save(c2);
        jpaCompanyRepository.save(c3);
        jpaCompanyRepository.save(c4);

        Company deleted = Company.create("DeletedOne", "Seoul", hubId, CompanyType.RECEIVER, userId);
        deleted.delete(userId);
        jpaCompanyRepository.save(deleted);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("정렬 조건 없을 때 기본 정렬(createdAt DESC) 적용")
    public void default_sorting_applies_createdAt_DESC() {
        PageRequest pageable = PageRequest.of(0, 10); // 정렬 명시 안함

        Page<Company> result = companyQueryRepository.searchCompanies(null, null, null, pageable);

        assertThat(result.getTotalElements()).isEqualTo(4); // soft deleted 제외
        assertThat(result.getContent().get(0).getName()).isEqualTo("Delta"); // 가장 마지막에 생성됨
        assertThat(result.getContent().get(3).getName()).isEqualTo("Alpha"); // 가장 먼저 생성됨
    }
}
