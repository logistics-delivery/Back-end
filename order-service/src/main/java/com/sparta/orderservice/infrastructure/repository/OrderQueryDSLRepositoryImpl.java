package com.sparta.orderservice.infrastructure.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.model.OrderStatus;
import com.sparta.orderservice.domain.repository.OrderQueryDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.sparta.orderservice.domain.model.QOrder;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryDSLRepositoryImpl implements OrderQueryDSLRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> searchOrders(String name, OrderStatus status) {
        QOrder order = QOrder.order;
        BooleanBuilder builder = new BooleanBuilder();

        if (name != null && !name.isBlank()) {
            builder.and(order.name.containsIgnoreCase(name));
        }

        if (status != null) {
            builder.and(order.status.eq(status));
        }

        return queryFactory.selectFrom(order)
                .where(builder)
                .fetch();
    }
}
