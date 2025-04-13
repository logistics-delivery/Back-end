package com.sparta.shippingmanager.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerSearchCondition;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerSearchResult;
import com.sparta.shippingmanager.domain.model.ManagerType;
import com.sparta.shippingmanager.domain.model.QShippingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ShippingManagerSearchRepository {

    private final JPAQueryFactory queryFactory;

    public ShippingManagerSearchResult search(ShippingManagerSearchCondition condition) {
        QShippingManager sm = QShippingManager.shippingManager;
        int validPageSize = switch (condition.getPageSize()) {
            case 10, 30, 50 -> condition.getPageSize();
            default -> 10;
        };
        int page = Math.max(condition.getPage(), 0);
        // 1. 리스트 조회
        List<ShippingManagerResponseDto> content = queryFactory
                .select(Projections.constructor(ShippingManagerResponseDto.class,
                        sm.managerType,
                        sm.shippingManagerId,
                        sm.shippingOrder
                ))
                .from(sm)
                .where(
                        eqShippingManagerId(condition.getShippingManagerId()),
                        eqManagerType(condition.getManagerType()),
                        eqShippingOrder(condition.getShippingOrder())
                )
                .orderBy(resolveSort(condition.getSortBy(), sm))
                .offset((long) page * validPageSize)
                .limit(validPageSize)
                .fetch();
        // 2. 카운트 조회
        Long total = queryFactory
                .select(sm.count())
                .from(sm)
                .where(
                        eqShippingManagerId(condition.getShippingManagerId()),
                        eqManagerType(condition.getManagerType()),
                        eqShippingOrder(condition.getShippingOrder())
                )
                .fetchOne();
        return ShippingManagerSearchResult.builder()
                .content(content)
                .page(page)
                .pageSize(validPageSize)
                .totalCount(Optional.ofNullable(total).orElse(0L))
                .build();
    }
    private BooleanExpression eqShippingManagerId(UUID id) {
        return id != null ? QShippingManager.shippingManager.shippingManagerId.eq(id) : null;
    }
    private BooleanExpression eqManagerType(ManagerType type) {
        return type != null ? QShippingManager.shippingManager.managerType.eq(type) : null;
    }
    private BooleanExpression eqShippingOrder(Integer order) {
        return order != null ? QShippingManager.shippingManager.shippingOrder.eq(order) : null;
    }
    private OrderSpecifier<?> resolveSort(String sortBy, QShippingManager sm) {
        if ("modifiedAt".equalsIgnoreCase(sortBy)) {
            return sm.updatedAt.desc();
        }
        return sm.createdAt.desc();
    }
}











