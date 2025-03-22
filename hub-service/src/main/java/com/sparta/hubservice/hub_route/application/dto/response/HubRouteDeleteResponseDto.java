package com.sparta.hubservice.hub_route.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class HubRouteDeleteResponseDto {

    private final UUID hub_route_id;
    private final String message;

    public HubRouteDeleteResponseDto(UUID hub_route_id, String message) {
        this.hub_route_id = hub_route_id;
        this.message = message;
    }

}
