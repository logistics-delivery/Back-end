package com.sparta.orderservice.infrastructure.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

//Product 서비스에서 재고 차감 요청 후 반환되는 응답 DTO

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecreaseProductQuantityResponseDto {

    private UUID productId;             // 재고 감소 상품 ID
    private Boolean isSuccess;          // 재고 감소 성공 여부
    private Integer decreasedQuantity;  // 실제 차감된 수량
    private String message;             // 성공/실패 메시지
}
