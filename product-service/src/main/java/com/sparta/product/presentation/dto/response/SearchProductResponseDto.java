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
                                       Boolean isDisplay,
                                       UUID companyId) {
    /**
     * Converts a Product entity into a SearchProductResponseDto.
     *
     * <p>This method maps the essential fields from the given Product—specifically, the id, name, description,
     * price, display flag, and companyId—into a new SearchProductResponseDto instance using the builder pattern.</p>
     *
     * @param product the product entity to convert
     * @return a SearchProductResponseDto populated with data from the product
     */
    public static SearchProductResponseDto from(Product product) {
        return SearchProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isDisplay(product.isDisplay())
                .companyId(product.getCompanyId())
                .build();
    }
}
