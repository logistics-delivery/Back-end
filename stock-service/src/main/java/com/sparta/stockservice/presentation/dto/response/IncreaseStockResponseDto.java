package com.sparta.stockservice.presentation.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IncreaseStockResponseDto(UUID productId,            // 재고 증가 상품
                                       Boolean isSuccess,         // 재고 증가 성공 여부
                                       Integer increasedQuantity, // 실제 증가된 수량
                                       String message) {          // 성공 or 실패 메세지
    // Entity -> DTO 변환 메서드
    // 성공 응답
    public static IncreaseStockResponseDto success(UUID productId, int increasedQuantity) {
        return IncreaseStockResponseDto.builder()
                .productId(productId)
                .isSuccess(true)
                .increasedQuantity(increasedQuantity)
                .message("재고 증가 성공.")
                .build();
    }

    // 실패 응답
    public static IncreaseStockResponseDto failure(UUID productId) {
        return IncreaseStockResponseDto.builder()
                .productId(productId)
                .isSuccess(false)
                .increasedQuantity(0)
                .message("재고 증가 실패.")
                .build();
    }
}
