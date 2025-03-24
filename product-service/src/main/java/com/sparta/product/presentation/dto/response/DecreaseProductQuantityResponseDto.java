package com.sparta.product.presentation.dto.response;

import com.sparta.product.domain.model.Product;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DecreaseProductQuantityResponseDto(UUID productId,            // 재고 감소 상품
                                                 Boolean isSuccess,         // 재고 감소 성공 여부
                                                 Integer decreasedQuantity,	// 실제 차감된 수량
                                                 String message) {          // 성공 or 실패 메세지
    // Entity -> DTO 변환 메서드
    // 성공 응답
    public static DecreaseProductQuantityResponseDto success(Product product, int decreasedQuantity) {
        return DecreaseProductQuantityResponseDto.builder()
                .productId(product.getId())
                .isSuccess(true)
                .decreasedQuantity(decreasedQuantity)
                .message("재고 감소 성공.")
                .build();
    }

    // 실패 응답
    public static DecreaseProductQuantityResponseDto failure(Product product) {
        return DecreaseProductQuantityResponseDto.builder()
                .productId(product.getId())
                .isSuccess(false)
                .decreasedQuantity(0)
                .message("재고 감소 실패.")
                .build();
    }
}
