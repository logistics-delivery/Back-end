package com.sparta.orderservice.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private String name;
    private UUID supplierId;
    private UUID receiverId;
    private UUID productId;
    private BigDecimal totalPrice;
    private String requestDetail;
    private UUID createBy;
}
