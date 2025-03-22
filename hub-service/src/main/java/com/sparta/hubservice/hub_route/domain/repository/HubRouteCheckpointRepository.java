package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import java.util.List;
import java.util.UUID;

public interface HubRouteCheckpointRepository {

    List<HubRouteCheckpoint> findAllByHubRouteId(UUID hubRouteId);

    void saveAll(List<HubRouteCheckpoint> checkpointList);
}
