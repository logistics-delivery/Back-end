package com.sparta.shippingservice.infrastructure.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.shippingservice.application.dto.request.ShippingSearchCondition;
import com.sparta.shippingservice.application.dto.response.ShippingSearchResult;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparta.shippingservice.domain.model.QShipping.shipping;

@Repository
@RequiredArgsConstructor
public class ShippingSearchRepository {
    private final JPAQueryFactory queryFactory;


    public ShippingSearchResult search(ShippingSearchCondition condition) {
        int validPageSize = switch (condition.getPageSize()) {
            case 10, 30, 50 -> condition.getPageSize();
            default -> 10;
        };
        int page = condition.getPage();
        List<Shipping> content = queryFactory
                .selectFrom(shipping)
                .where(
                        containsShippingAddress(condition.getShippingAddress()),
                        containsReceiverName(condition.getReceiverName()),
                        eqStatus(condition.getStatus())
                )
                .orderBy(resolveSort(condition.getSortBy()))
                .offset((long) page * validPageSize)
                .limit(validPageSize)
                .fetch();
        Long total = queryFactory
                .select(shipping.count())
                .from(shipping)
                .where(
                        containsShippingAddress(condition.getShippingAddress()),
                        containsReceiverName(condition.getReceiverName()),
                        eqStatus(condition.getStatus())
                )
                .fetchOne();
        return ShippingSearchResult.builder()
                .content(content)
                .page(page)
                .pageSize(validPageSize)
                .totalCount(total)
                .build();
    }



    private BooleanExpression containsShippingAddress(String address) {
        return StringUtils.hasText(address) ? shipping.shippingAddress.containsIgnoreCase(address) : null;
    }
    private BooleanExpression containsReceiverName(String name) {
        return StringUtils.hasText(name) ? shipping.receiverName.eq(name) : null;
    }

    private BooleanExpression eqStatus(ShippingStatus status) {
        return status != null ? shipping.status.eq(status) : null;
    }
    private OrderSpecifier<?> resolveSort(String sortBy) {
        if ("modifiedAt".equalsIgnoreCase(sortBy)) {
            return shipping.updatedAt.desc();
        }
        return shipping.createdAt.desc(); // default
    }
}




























