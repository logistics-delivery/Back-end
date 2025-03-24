package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteRepository {

    Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);

    Optional<HubRoute> findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId);

    <S extends HubRoute> S save(S hubRoute);

    Optional<Page<HubRoute>> findAllByIsDeletedFalse(Pageable pageable);

    Optional<HubRoute> findById(java.util.UUID hubRouteId);

    Optional<List<HubRoute>> findByFromHub(Hub hub);

    Optional<HubRoute> findShortestRouteByFromAndTo(Hub fromHub, Hub toHub);
}
