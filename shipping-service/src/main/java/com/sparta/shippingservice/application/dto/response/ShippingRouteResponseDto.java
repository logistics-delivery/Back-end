package com.sparta.shippingservice.application.dto.response;
import com.sparta.shippingservice.domain.model.ShippingCheckpoint;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Builder
public record ShippingRouteResponseDto(
        UUID id,
        UUID hubRouteId,
        UUID fromHubId,
        UUID toHubId,
        int duration,
        BigDecimal distance,
        List<CheckpointResponse> checkpoints
) {
    public static ShippingRouteResponseDto from(ShippingRouteLog routeLog) {
        return ShippingRouteResponseDto.builder()
                .id(routeLog.getId())
                .hubRouteId(routeLog.getHubRouteId())
                .fromHubId(routeLog.getFromHubId())
                .toHubId(routeLog.getToHubId())
                .duration(routeLog.getDuration())
                .distance(routeLog.getDistance())
                .checkpoints(
                        routeLog.getCheckpoints().stream()
                                .map(CheckpointResponse::from)
                                .toList()
                )
                .build();
    }
    @Builder
    public record CheckpointResponse(
            int orderIndex,
            UUID hubId,
            String hubName
    ) {
        public static CheckpointResponse from(ShippingCheckpoint cp) {
            return CheckpointResponse.builder()
                    .orderIndex(cp.getOrderIndex())
                    .hubId(cp.getHubId())
                    .hubName(cp.getHubName())
                    .build();
        }
    }
}














