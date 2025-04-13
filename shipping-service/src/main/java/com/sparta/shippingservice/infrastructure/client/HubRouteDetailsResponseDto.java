package com.sparta.shippingservice.infrastructure.client;


import com.sparta.shippingservice.domain.model.ShippingCheckpoint;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HubRouteDetailsResponseDto {
    private UUID hubRouteId;
    private UUID fromHubId;
    private UUID toHubId;
    private int duration;
    private BigDecimal distance;
    private List<CheckpointResponseDto> checkpoints;
    public HubRouteDetailsResponseDto(HubRoute hubRoute, List<CheckpointResponseDto> checkpoints) {
        this.hubRouteId = hubRoute.getHubRouteId();
        this.fromHubId = hubRoute.getFromHub().getHubId();
        this.toHubId = hubRoute.getToHub().getHubId();
        this.duration = hubRoute.getDuration();
        this.distance = hubRoute.getDistance();
        this.checkpoints = checkpoints;
    }

    public ShippingRouteLog toShippingRouteLog() {
        ShippingRouteLog routeLog = ShippingRouteLog.builder()
                .hubRouteId(hubRouteId)
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .duration(duration)
                .distance(distance)
                .build();
        if (checkpoints != null) {
            for (CheckpointResponseDto cp : checkpoints) {
                ShippingCheckpoint checkpoint = ShippingCheckpoint.builder()
                        .orderIndex(cp.getOrder())
                        .hubId(cp.getHubId())
                        .hubName(cp.getHubName())
                        .build();
                routeLog.addCheckpoint(checkpoint);
            }
        }
        return routeLog;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    public static class CheckpointResponseDto {
        private final int order;
        private final UUID hubId;
        private final String hubName;
    }
    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    public static class HubRoute {
        private final UUID hubRouteId;
        private final Hub fromHub;
        private final Hub toHub;
        private final int duration;
        private final BigDecimal distance;
    }
    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Hub {
        private final UUID hubId;
        private final String name;
        private final String address;
        private final BigDecimal latitude;
        private final BigDecimal longitude;
    }
}








