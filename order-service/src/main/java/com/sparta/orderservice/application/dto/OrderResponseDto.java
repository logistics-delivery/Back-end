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
    private BigDecimal totolPrice;
    private OrderStatus status;
    private String requestDetail;
    private LocalDateTime createdAt;
    private UUID createdBy;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.name = order.getName();
        this.supplierId = order.getSupplierId();
        this.receiverId = order.getReceiverId();
        this.productId = order.getProductId();
        this.totolPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.requestDetail = order.getRequestDetail();
        this.createdAt = order.getCreatedAt();
        this.createdBy = order.getCreatedBy();
    }
}


