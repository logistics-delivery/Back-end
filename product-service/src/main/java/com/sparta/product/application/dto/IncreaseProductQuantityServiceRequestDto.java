package com.sparta.product.application.dto;

import com.sparta.product.presentation.dto.request.IncreaseProductQuantityRequestDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record IncreaseProductQuantityServiceRequestDto(UUID productId,
                                                       UUID companyId,
                                                       UUID hubId,
                                                       Integer quantity) {

    // 요청 DTO -> 서비스 DTO 변환 메서드
    public static IncreaseProductQuantityServiceRequestDto of(IncreaseProductQuantityRequestDto request, UUID productId) {
        return IncreaseProductQuantityServiceRequestDto.builder()
                .productId(productId)
                .companyId(request.companyId())
                .hubId(request.hubId())
                .quantity(request.quantity())
                .build();
    }
}
