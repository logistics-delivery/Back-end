package com.sparta.orderservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID orderItemId;  // 주문 상세 ID

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // 주문 ID

    @Column(nullable = false)
    private UUID productId;  // 상품 ID

    @Column(nullable = false)
    private int quantity;  // 주문 수량

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice;  // 삼품 금액
}

