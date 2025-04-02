package com.sparta.product.presentation.dto.response;


import com.sparta.product.domain.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreateProductResponseDto(UUID id,
                                       String name,
                                       String description,
                                       BigDecimal price,
                                       boolean isDisplay,
                                       UUID companyId) {

    // Entity -> DTO 변환 메서드
    public static CreateProductResponseDto from(Product product) {
        return CreateProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .isDisplay(product.isDisplay())
            .companyId(product.getCompanyId())
            .build();
    }
}
