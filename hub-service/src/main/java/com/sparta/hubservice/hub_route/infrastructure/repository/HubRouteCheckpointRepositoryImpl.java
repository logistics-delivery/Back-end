package com.sparta.hubservice.hub_route.infrastructure.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteCheckpointRepository;
import com.sparta.hubservice.hub_route.infrastructure.persistence.JPAHubRouteCheckpointRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRouteCheckpointRepositoryImpl implements HubRouteCheckpointRepository {

    private final JPAHubRouteCheckpointRepository jpaHubRouteCheckpointRepository;

    @Override
    public List<HubRouteCheckpoint> findAllByHubRoute(HubRoute hubRoute) {
        return jpaHubRouteCheckpointRepository.findAllByHubRoute(hubRoute);
    }

    @Override
    public void saveAll(List<HubRouteCheckpoint> checkpointList) {
        jpaHubRouteCheckpointRepository.saveAll(checkpointList);
    }

    @Override
    public List<HubRouteCheckpoint> findAllByHubRoute_OrderBySequenceAsc(HubRoute hubRoute) {
        return jpaHubRouteCheckpointRepository.findAllByHubRoute_OrderBySequenceAsc(hubRoute);
    }
}
