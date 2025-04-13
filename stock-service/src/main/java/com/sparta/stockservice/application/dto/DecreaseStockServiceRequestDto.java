package com.sparta.stockservice.application.dto;

import com.sparta.stockservice.presentation.dto.request.DecreaseStockRequestDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DecreaseStockServiceRequestDto(UUID productId,
                                             UUID companyId,
                                             UUID hubId,
                                             Integer quantity) {

    // 요청 DTO -> 서비스 DTO 변환 메서드
    public static DecreaseStockServiceRequestDto of(DecreaseStockRequestDto request, UUID productId) {
        return DecreaseStockServiceRequestDto.builder()
                .productId(productId)
                .companyId(request.companyId())
                .hubId(request.hubId())
                .quantity(request.quantity())
                .build();
    }
}
