package com.sparta.product.presentation.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record SearchProductRequestDto(String name,
                                      String description,
                                      BigDecimal price,
                                      Integer quantity,
                                      Boolean isDisplay,
                                      UUID companyId,
                                      UUID hubId) {
}

