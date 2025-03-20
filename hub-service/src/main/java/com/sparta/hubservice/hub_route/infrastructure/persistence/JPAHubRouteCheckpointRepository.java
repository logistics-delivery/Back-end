package com.sparta.hubservice.hub_route.infrastructure.persistence;

import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteCheckpointRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubRouteCheckpointRepository extends JpaRepository<HubRouteCheckpoint, Long>,
                                                            HubRouteCheckpointRepository {


}
