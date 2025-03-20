package com.sparta.product.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DeleteProductServiceRequestDto(Long userId,
                                             UUID productId) {

    // 요청 값 -> 서비스 DTO 변환 메서드
    public static DeleteProductServiceRequestDto of(Long userId,
                                                    UUID productId) {
        return DeleteProductServiceRequestDto.builder()
                .userId(userId)
                .productId(productId)
                .build();
    }
}
