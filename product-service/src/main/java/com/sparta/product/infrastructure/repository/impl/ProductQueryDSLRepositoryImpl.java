package com.sparta.product.infrastructure.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.product.domain.model.Product;
import com.sparta.product.infrastructure.repository.ProductQueryDSLRepository;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.SearchProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sparta.product.domain.model.QProduct.product;

@RequiredArgsConstructor
public class ProductQueryDSLRepositoryImpl implements ProductQueryDSLRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto,  Pageable pageable) {

        // 동적 정렬 조건 생성
        List<OrderSpecifier<?>> orderSpecifierList = dynamicOrder(pageable);

        // 유효한 페이지 크기 설정
        int pageSize = validatePageSize(pageable.getPageSize());


        // 동적 쿼리 생성 및 조회
        List<Product> resultList = queryFactory
                .selectFrom(product)
                .where(
                        nameContains(requestDto.name()),
                        descriptionContains(requestDto.description()),
                        companyIdEq(requestDto.companyId())
                )
                .orderBy(orderSpecifierList.toArray(new OrderSpecifier[0])) // 동적 정렬
                .offset(pageable.getOffset())  // 페이징 - 시작 인덱스
                .limit(pageSize)               // 페이징 - 페이지 크기
                .distinct()                    // 중복 제거
                .fetch();


        // 총 개수 조회 (count 쿼리 따로 실행)
        Long totalCount = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        nameContains(requestDto.name()),
                        descriptionContains(requestDto.description()),
                        companyIdEq(requestDto.companyId())
                )
                .fetchOne();

        if (totalCount == null) {
            totalCount = 0L;
        }

        // Product -> ProductSearchResponseDto 변환
        List<SearchProductResponseDto> content = resultList.stream()
                .map(SearchProductResponseDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, totalCount);
    }



    private BooleanExpression nameContains(String name) {
        return name != null ? product.name.containsIgnoreCase(name) : null;
    }


    private BooleanExpression descriptionContains(String description) {
        return description != null ? product.description.containsIgnoreCase(description) : null;
    }


    private BooleanExpression companyIdEq(UUID companyId) {
        return companyId != null ? product.companyId.eq(companyId) : null;
    }



    /**
     * 동적 정렬 조건 생성 메서드
     * @param pageable
     * @return
     */
    private List<OrderSpecifier<?>> dynamicOrder(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction
                        = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;

                switch (sortOrder.getProperty()) {
                    case "createdAt":  // 상품 생성일 기준 정렬
                        orderSpecifierList.add(new OrderSpecifier<>(direction, product.createdAt));
                        break;
                    case "updatedAt":  // 상품 업데이트 기준 정렬
                        orderSpecifierList.add(new OrderSpecifier<>(direction, product.updatedAt));
                        break;
                    default:  // 잘못된 정렬 필드 처리
                        throw new IllegalArgumentException(
                                "잘못된 정렬 필드입니다. : " + sortOrder.getProperty());
                }
            }
        } else {
            // 기본 정렬: 오름차순, createdAt
            orderSpecifierList.add(new OrderSpecifier<>(com.querydsl.core.types.Order.ASC, product.createdAt));
        }
        return orderSpecifierList;
    }


    /**
     * 유효한 페이지 크기 검증 및 설정
     * @param pageSize : 요청된 페이지 크기
     * @return int : 유효한 페이지 크기
     */
    private int validatePageSize(int pageSize) {
        return Set.of(10, 30, 50).contains(pageSize) ? pageSize : 10;
    }


}
