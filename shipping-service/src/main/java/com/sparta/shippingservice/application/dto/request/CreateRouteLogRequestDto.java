package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.ShippingCheckpoint;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CreateRouteLogRequestDto {
    private UUID hubRouteId;
    private UUID fromHubId;
    private UUID toHubId;
    private int duration;
    private BigDecimal distance;
    private List<CheckpointSaveDto> checkpoints;

    public ShippingRouteLog toEntity() {
        ShippingRouteLog route = ShippingRouteLog.builder()
                .hubRouteId(hubRouteId)
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .duration(duration)
                .distance(distance)
                .build();
        // 체크포인트 추가
        if (checkpoints != null) {
            for (CheckpointSaveDto cp : checkpoints) {
                route.addCheckpoint(cp.toEntity());
            }
        }
        return route;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CheckpointSaveDto {
        private int orderIndex;
        private UUID hubId;
        private String hubName;
        public ShippingCheckpoint toEntity() {
            return ShippingCheckpoint.builder()
                    .orderIndex(orderIndex)
                    .hubId(hubId)
                    .hubName(hubName)
                    .build();
        }
    }
}











