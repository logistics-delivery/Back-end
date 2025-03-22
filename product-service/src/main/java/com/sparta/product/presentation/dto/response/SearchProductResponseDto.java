package com.sparta.product.presentation.dto.response;

import com.sparta.product.domain.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record SearchProductResponseDto(UUID id,
                                       String name,
                                       String description,
                                       BigDecimal price,
                                       Integer quantity,
                                       Boolean isDisplay,
                                       UUID companyId,
                                       UUID hubId) {
    // Entity -> DTO 변환 메서드
    public static SearchProductResponseDto from(Product product) {
        return SearchProductResponseDto.builder()
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
