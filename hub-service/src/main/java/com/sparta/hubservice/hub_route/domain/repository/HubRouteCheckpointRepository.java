package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import java.util.List;

public interface HubRouteCheckpointRepository {

    List<HubRouteCheckpoint> findAllByHubRoute(HubRoute hubRoute);

    void saveAll(List<HubRouteCheckpoint> checkpointList);

    List<HubRouteCheckpoint> findAllByHubRoute_OrderBySequenceAsc(HubRoute hubRoute);

    void save(HubRouteCheckpoint result);
}
