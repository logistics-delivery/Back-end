package com.sparta.stockservice.presentation.dto.request;

import java.util.UUID;

public record CreateStockRequestDto(UUID productId,
                                    UUID hubId,
                                    Integer quantity) {
}
