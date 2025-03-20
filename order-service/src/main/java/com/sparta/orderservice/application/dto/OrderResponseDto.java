package com.sparta.orderservice.application.dto;

import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private UUID orderId;
    private String name;
    private UUID supplierId;
    private UUID receiverId;
    private UUID productId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String requestDetail;
    private LocalDateTime createdAt;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.name = order.getName();
        this.supplierId = order.getSupplierId();
        this.receiverId = order.getReceiverId();
        this.productId = order.getProductId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.requestDetail = order.getRequestDetail();
        this.createdAt = order.getCreatedAt();
    }

    // 더 직관적인 DTO 변환
    public static OrderResponseDto fromEntity(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .name(order.getName())
                .supplierId(order.getSupplierId())
                .receiverId(order.getReceiverId())
                .productId(order.getProductId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .requestDetail(order.getRequestDetail())
                .createdAt(order.getCreatedAt())
                .build();
    }
}


