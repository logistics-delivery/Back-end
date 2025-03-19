package com.sparta.product.presentation.dto.request;

import java.math.BigDecimal;

public record UpdateProductRequestDto(String name,
                                      String description,
                                      BigDecimal price,
                                      boolean isDisplay) {
}
