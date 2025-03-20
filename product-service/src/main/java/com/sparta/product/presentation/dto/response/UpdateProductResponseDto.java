package com.sparta.product.presentation.dto.response;

import com.sparta.product.domain.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record UpdateProductResponseDto(UUID id,
                                       String name,
                                       String description,
                                       BigDecimal price,
                                       boolean isDisplay) {

    // Entity -> DTO 변환 메서드
    public static UpdateProductResponseDto from(Product product) {
        return UpdateProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isDisplay(product.isDisplay())
                .build();
    }
}
