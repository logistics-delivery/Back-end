package com.sparta.product.application.dto;


import com.sparta.product.presentation.dto.request.UpdateProductRequestDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record UpdateProductServiceRequestDto(UUID id,
                                             String name,
                                             String description,
                                             BigDecimal price,
                                             boolean isDisplay
) {

    // 요청 DTO -> 서비스 DTO 변환 메서드
    public static UpdateProductServiceRequestDto of(UpdateProductRequestDto request, UUID productId) {
        return UpdateProductServiceRequestDto.builder()
                .id(productId)
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .isDisplay(request.isDisplay())
                .build();
    }
}
