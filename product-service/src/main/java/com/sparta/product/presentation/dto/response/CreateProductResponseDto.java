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
                                       Integer quantity,
                                       boolean isDisplay,
                                       UUID companyId,
                                       UUID hubId) {

    // Entity -> DTO 변환 메서드
    public static CreateProductResponseDto from(Product product) {
        return CreateProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .isDisplay(product.isDisplay())
            .companyId(product.getCompanyId())
            .hubId(product.getHubId())
            .build();
    }
}
