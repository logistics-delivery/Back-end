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

    /**
     * Converts a Product entity to a CreateProductResponseDto.
     *
     * <p>This method maps the attributes of the provided Product, including id, name, description, price,
     * display status, and company ID, into a new CreateProductResponseDto using the builder pattern.</p>
     *
     * @param product the Product entity containing the product details
     * @return a CreateProductResponseDto instance representing the product
     */
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
