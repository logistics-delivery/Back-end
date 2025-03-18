package com.sparta.orderservice.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateOrderRequestDto {
    private String name;
    private UUID supplierId;
    private UUID receiverID;
    private UUID productId;
    private BigDecimal totalPrice;
    private String requestDetail;
}
