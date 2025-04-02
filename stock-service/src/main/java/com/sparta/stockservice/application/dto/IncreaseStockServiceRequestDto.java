package com.sparta.stockservice.application.dto;

import com.sparta.stockservice.presentation.dto.request.IncreaseStockRequestDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record IncreaseStockServiceRequestDto(UUID productId,
                                             UUID companyId,
                                             UUID hubId,
                                             Integer quantity) {

    // 요청 DTO -> 서비스 DTO 변환 메서드
    public static IncreaseStockServiceRequestDto of(IncreaseStockRequestDto request, UUID productId) {
        return IncreaseStockServiceRequestDto.builder()
                .productId(productId)
                .companyId(request.companyId())
                .hubId(request.hubId())
                .quantity(request.quantity())
                .build();
    }
}
