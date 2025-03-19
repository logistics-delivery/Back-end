package com.sparta.hubservice.hub.application.dto.response;

import com.sparta.hubservice.hub.domain.model.HubRoute;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HubRouteResponse {

    private final UUID routeId;
    private final UUID fromHubId;
    private final UUID toHubId;
    private final int duration;
    private final BigDecimal distance;

    public HubRouteResponse(HubRoute hubRoute) {
        this.routeId = hubRoute.getHubRouteId();
        this.fromHubId = hubRoute.getFromHub().getHubId();
        this.toHubId = hubRoute.getToHub().getHubId();
        this.duration = hubRoute.getDuration();
        this.distance = hubRoute.getDistance();
    }

}
