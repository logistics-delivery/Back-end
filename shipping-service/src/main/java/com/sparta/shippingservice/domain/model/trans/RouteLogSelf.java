package com.sparta.shippingservice.domain.model.trans;

import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;

import java.math.BigDecimal;
import java.util.UUID;

public record RouteLogSelf(
        Shipping shipping,
        UUID startHubId,
        UUID endHubId,
        Integer sequence,
        BigDecimal estimatedDistance,
        Integer estimatedTime,
        BigDecimal actualDistance,
        Integer actualTime,
        UUID shippingManagerId
) {
    public ShippingRouteLog toShippingRouteLog() {
        return new ShippingRouteLog(
                this.shipping,
                this.startHubId,
                this.endHubId,
                this.sequence,
                this.estimatedDistance,
                this.estimatedTime,
                this.actualDistance,
                this.actualTime,
                this.shippingManagerId
        );
    }
}














