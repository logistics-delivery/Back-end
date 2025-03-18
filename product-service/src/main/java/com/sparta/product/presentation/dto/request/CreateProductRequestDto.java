package com.sparta.product.presentation.dto.request;

import java.math.BigDecimal;
import java.util.UUID;


public record CreateProductRequestDto(String name,
                                      String description,
                                      BigDecimal price,
                                      Integer quantity,
                                      boolean isDisplay,
                                      UUID companyId,
                                      UUID hubId) {

}
