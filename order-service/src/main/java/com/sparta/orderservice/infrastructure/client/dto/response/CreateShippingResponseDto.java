package com.sparta.orderservice.infrastructure.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

//배송 생성 응답 DTO
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShippingResponseDto {

    private UUID shippingId; // 생성된 배송 ID
    private String status;   // 배송 상태 (예: READY, SHIPPING 등)
    private String message;  // 성공/실패 메시지
    private String route;            // 배송 경로
    private String hubName;          // 허브 이름
    private String hubManagerName;   // 허브 담당자 이름

}
