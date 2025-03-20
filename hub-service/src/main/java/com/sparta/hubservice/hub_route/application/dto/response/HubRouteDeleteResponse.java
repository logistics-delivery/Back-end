package com.sparta.hubservice.hub_route.application.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
public class HubRouteDeleteResponse {

    private final UUID hub_route_id;
    private final String message;

    public HubRouteDeleteResponse(UUID hub_route_id, String message) {
        this.hub_route_id = hub_route_id;
        this.message = message;
    }

}
