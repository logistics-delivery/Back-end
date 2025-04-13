package com.sparta.shippingservice.infrastructure.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.shippingservice.application.dto.request.ShippingRouteSearchCondition;
import com.sparta.shippingservice.application.dto.response.ShippingLogSearchResult;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.domain.model.QShippingRouteLog;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ShippingLogSearchRepository {

    private final JPAQueryFactory queryFactory;


    public ShippingLogSearchResult search(ShippingRouteSearchCondition condition) {
        QShippingRouteLog log = QShippingRouteLog.shippingRouteLog;
        int validPageSize = switch (condition.getPageSize()) {
            case 10, 30, 50 -> condition.getPageSize();
            default -> 10;
        };
        int page = Math.max(condition.getPage(), 0);
        List<ShippingRouteLog> logs = queryFactory
                .selectFrom(log)
                .where(
                        eqShippingId(condition.getShippingId()),
                        eqStartHubId(condition.getFromHubId()),
                        eqEndHubId(condition.getToHubId()),
                        eqHubRouteId(condition.getHubRouteId())
                )
                .orderBy(resolveSort(condition.getSortBy(), log))
                .offset((long) page * validPageSize)
                .limit(validPageSize)
                .fetch();
        Long total = queryFactory
                .select(log.count())
                .from(log)
                .where(
                        eqShippingId(condition.getShippingId()),
                        eqStartHubId(condition.getFromHubId()),
                        eqEndHubId(condition.getToHubId()),
                        eqHubRouteId(condition.getHubRouteId())
                )
                .fetchOne();
        // 3. DTO 매핑
        List<ShippingRouteResponseDto> content = logs.stream()
                .map(ShippingRouteResponseDto::from)
                .toList();
        return ShippingLogSearchResult.builder()
                .content(content)
                .page(page)
                .pageSize(validPageSize)
                .totalCount(Optional.ofNullable(total).orElse(0L))
                .build();
    }
    private BooleanExpression eqShippingId(UUID id) {
        return id != null ? QShippingRouteLog.shippingRouteLog.shipping.id.eq(id) : null;
    }
    private BooleanExpression eqStartHubId(UUID id) {
        return id != null ? QShippingRouteLog.shippingRouteLog.fromHubId.eq(id) : null;
    }
    private BooleanExpression eqEndHubId(UUID id) {
        return id != null ? QShippingRouteLog.shippingRouteLog.toHubId.eq(id) : null;
    }
    private BooleanExpression eqHubRouteId(UUID id) {
        return id != null ? QShippingRouteLog.shippingRouteLog.hubRouteId.eq(id) : null;
    }
    private OrderSpecifier<?> resolveSort(String sortBy, QShippingRouteLog log) {
        if ("modifiedAt".equalsIgnoreCase(sortBy)) {
            return log.updatedAt.desc();
        }
        return log.createdAt.desc();
    }
}