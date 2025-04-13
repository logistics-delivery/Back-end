package com.sparta.shippingservice.application.dto.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRouteSearchCondition {
    private UUID shippingId;
    private UUID fromHubId;
    private UUID toHubId;
    private UUID hubRouteId;
    private String sortBy;     // "createdAt", "modifiedAt"
    private int pageSize;      // 10, 30, 50만 허용
    private int page;          // 0부터 시작
}
