package com.sparta.orderservice.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

// 배송 생성 요청 DTO
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShippingRequestDto {

    private UUID orderId;         // 주문 ID
    private UUID productId;       // 상품 ID
    private UUID supplierId;      // 출발지 (공급업체 ID)
    private UUID receiverId;      // 도착지 (수령업체 ID)
    private Integer quantity;     // 수량
}
