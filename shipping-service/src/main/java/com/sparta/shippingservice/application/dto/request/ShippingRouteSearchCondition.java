package com.sparta.shippingservice.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShippingRouteSearchCondition {
    private UUID shippingId;
    private UUID fromHubId;
    private UUID toHubId;
    private UUID hubRouteId;
}
