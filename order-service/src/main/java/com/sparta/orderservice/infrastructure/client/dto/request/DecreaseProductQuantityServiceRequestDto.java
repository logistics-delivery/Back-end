package com.sparta.orderservice.infrastructure.client.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DecreaseProductQuantityServiceRequestDto {

    private UUID productId;
    private UUID companyId;
    private UUID hubId;
    private Integer quantity;
}