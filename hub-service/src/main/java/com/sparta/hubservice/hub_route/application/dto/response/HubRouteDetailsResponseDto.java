package com.sparta.hubservice.hub_route.application.dto.response;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HubRouteDetailsResponseDto {

    private final HubRoute hubRoute;
    private final List<HubRouteCheckpoint> checkpointList;

}
