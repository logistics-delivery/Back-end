package com.sparta.shippingservice.application.dto.client;

import java.math.BigDecimal;
import java.util.UUID;

public record HubRouteInfoResponse(
        UUID startHubId,
        UUID endHubId,
        BigDecimal estimatedDistance,
        Integer estimatedTime
) {}
