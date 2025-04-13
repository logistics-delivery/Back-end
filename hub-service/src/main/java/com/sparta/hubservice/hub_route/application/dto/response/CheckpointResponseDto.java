package com.sparta.hubservice.hub_route.application.dto.response;

import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CheckpointResponseDto {

    private final UUID hubRouteCheckpointId;
    private final UUID hubRouteId;
    private final UUID checkpointHubId;
    private final int sequence;

    public CheckpointResponseDto(HubRouteCheckpoint checkpoint) {
        hubRouteCheckpointId = checkpoint.getHubRouteCheckpointId();
        hubRouteId = checkpoint.getHubRoute().getHubRouteId();
        sequence = checkpoint.getSequence();
        checkpointHubId = checkpoint.getCheckpointHubId().getHubId();
    }
}
