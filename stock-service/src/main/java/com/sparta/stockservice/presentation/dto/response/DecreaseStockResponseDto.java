package com.sparta.stockservice.presentation.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DecreaseStockResponseDto(UUID productId,            // 재고 감소 상품
                                       Boolean isSuccess,         // 재고 감소 성공 여부
                                       Integer decreasedQuantity, // 실제 차감된 수량
                                       String message) {          // 성공 or 실패 메세지
    // Entity -> DTO 변환 메서드
    // 성공 응답
    public static DecreaseStockResponseDto success(UUID productId, int decreasedQuantity) {
        return DecreaseStockResponseDto.builder()
                .productId(productId)
                .isSuccess(true)
                .decreasedQuantity(decreasedQuantity)
                .message("재고 감소 성공.")
                .build();
    }

    // 실패 응답
    public static DecreaseStockResponseDto failure(UUID productId) {
        return DecreaseStockResponseDto.builder()
                .productId(productId)
                .isSuccess(false)
                .decreasedQuantity(0)
                .message("재고 감소 실패.")
                .build();
    }
}
