package com.sparta.shippingservice.application.dto.response;

import com.sparta.shippingservice.domain.model.ShippingRouteLog;

import java.math.BigDecimal;
import java.util.UUID;

public record ShippingRouteResponseDto(
        UUID id,
        UUID startHubId,
        UUID endHubId,
        Integer sequence,
        BigDecimal estimatedDistance,
        Integer estimatedTime,
        BigDecimal actualDistance,
        Integer actualTime,
        UUID shippingManagerId
) {
    public static ShippingRouteResponseDto from(ShippingRouteLog shippingRouteLog) {
        return new ShippingRouteResponseDto(
                shippingRouteLog.getId(),
                shippingRouteLog.getStartHubId(),
                shippingRouteLog.getEndHubId(),
                shippingRouteLog.getSequence(),
                shippingRouteLog.getEstimatedDistance(),
                shippingRouteLog.getEstimatedTime(),
                shippingRouteLog.getActualDistance(),
                shippingRouteLog.getActualTime(),
                shippingRouteLog.getShippingManagerId()
        );
    }

}
