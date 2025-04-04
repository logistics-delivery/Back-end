package com.sparta.product.presentation.dto.response;


import com.sparta.product.domain.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ReadProductResponseDto(UUID id,
                                     String name,
                                     String description,
                                     BigDecimal price,
                                     boolean isDisplay,
                                     UUID companyId) {

    /**
     * Converts the given Product entity into a ReadProductResponseDto.
     *
     * <p>This method maps the product’s core attributes—its ID, name, description, price,
     * display status, and company ID—to a corresponding DTO instance using the builder pattern.</p>
     *
     * @param product the product entity to convert
     * @return a ReadProductResponseDto representing the provided product data
     */
    public static ReadProductResponseDto from(Product product) {
        return ReadProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isDisplay(product.isDisplay())
                .companyId(product.getCompanyId())
                .build();
    }
}

