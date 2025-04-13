package com.sparta.shippingservice.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.shippingservice.application.dto.request.ShippingRouteSearchCondition;
import com.sparta.shippingservice.domain.model.QShippingRouteLog;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import com.sparta.shippingservice.domain.repository.ShippingRouteQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShippingRouteQueryRepositoryImpl implements ShippingRouteQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ShippingRouteLog> search(ShippingRouteSearchCondition condition, Pageable pageable) {
        QShippingRouteLog log = QShippingRouteLog.shippingRouteLog;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(log.deletedAt.isNull());

        if (condition.getShippingId() != null) {
            builder.and(log.shipping.id.eq(condition.getShippingId()));
        }

        if (condition.getFromHubId() != null) {
            builder.and(log.fromHubId.eq(condition.getFromHubId()));
        }

        if (condition.getToHubId() != null) {
            builder.and(log.toHubId.eq(condition.getToHubId()));
        }

        if (condition.getHubRouteId() != null) {
            builder.and(log.hubRouteId.eq(condition.getHubRouteId()));
        }

        List<ShippingRouteLog> results = queryFactory
                .selectFrom(log)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortedColumn(pageable.getSort()))
                .fetch();

        long total = queryFactory
                .select(log.id.count())
                .from(log)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?>[] getSortedColumn(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder<ShippingRouteLog> entityPath = new PathBuilder<>(ShippingRouteLog.class, "shippingRouteLog");

        if (sort != null && sort.isSorted()) {
            for (Sort.Order order : sort) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                String property = order.getProperty();
                orderSpecifiers.add(new OrderSpecifier(direction, entityPath.get(property)));
            }
        }

        // 생성일 내림차순으로 기본 정렬
        if (orderSpecifiers.isEmpty()) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, entityPath.get("createdAt")));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
