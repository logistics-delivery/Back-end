package com.sparta.hubservice.hub_route.infrastructure.persistence;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteCheckpointRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubRouteCheckpointRepository extends JpaRepository<HubRouteCheckpoint, Long>{

    List<HubRouteCheckpoint> findAllByHubRoute(HubRoute hubRouteId);

    List<HubRouteCheckpoint> findAllByHubRoute_OrderBySequenceAsc(HubRoute hubRoute);
}
