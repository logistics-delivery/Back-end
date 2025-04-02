package com.sparta.stockservice.presentation.dto.response;


import com.sparta.stockservice.domain.model.Stock;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateStockResponseDto(UUID id,
                                     UUID productId,
                                     UUID hubId,
                                     Integer quantity) {

    // Entity -> DTO 변환 메서드
    public static CreateStockResponseDto from(Stock stock) {
        return CreateStockResponseDto.builder()
                .id(stock.getId())
                .productId(stock.getProductId())
                .hubId(stock.getHubId())
                .quantity(stock.getQuantity())
                .build();
    }
}
