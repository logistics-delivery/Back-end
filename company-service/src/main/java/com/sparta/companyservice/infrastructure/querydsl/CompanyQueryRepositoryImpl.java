package com.sparta.companyservice.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.domain.model.QCompany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> searchCompanies(String name, String address, CompanyType companyType, Pageable pageable) {
        QCompany company = QCompany.company;

        try {
            // 조건 빌더 생성
            BooleanBuilder conditions = createConditions(company, name, address, companyType);

            // 결과 조회
            List<Company> results = queryFactory
                    .selectFrom(company)
                    .where(conditions)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getSortedColumn(pageable.getSort()))
                    .fetch();

            // 전체 카운트 조회
            Long countResult = queryFactory
                    .select(company.count())
                    .from(company)
                    .where(conditions)
                    .fetchOne();

            long total = countResult != null ? countResult : 0L;

            return new PageImpl<>(results, pageable, total);
        } catch (Exception e) {
            log.error("업체 검색 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("업체 검색 도중 오류가 발생했습니다.");
        }
    }

    /**
     * 검색 조건 생성
     */
    private BooleanBuilder createConditions(QCompany company, String name, String address, CompanyType companyType) {
        BooleanBuilder builder = new BooleanBuilder();

        // 소프트 삭제 제외
        builder.and(company.deletedAt.isNull());

        if (name != null && !name.isBlank()) {
            builder.and(company.name.containsIgnoreCase(name));
        }

        if (address != null && !address.isBlank()) {
            builder.and(company.address.containsIgnoreCase(address));
        }

        if (companyType != null) {
            builder.and(company.type.eq(companyType));
        }

        return builder;
    }

    /**
     * 정렬 지정자 생성
     */
    private OrderSpecifier<?>[] getSortedColumn(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder<Company> entityPath = new PathBuilder<>(Company.class, "company");

        // Sort가 비어있지 않은 경우에만 처리
        if (sort != null && sort.isSorted()) {
            for (Sort.Order order : sort) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                String property = order.getProperty();

                // 제네릭 타입의 OrderSpecifier 생성
                orderSpecifiers.add(
                        new OrderSpecifier(direction, entityPath.get(property))
                );
            }
        }

        // 기본 정렬(ID 오름차순) 추가
        if (orderSpecifiers.isEmpty()) {
            orderSpecifiers.add(
                    new OrderSpecifier(Order.DESC, entityPath.get("createdAt"))
            );

        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}