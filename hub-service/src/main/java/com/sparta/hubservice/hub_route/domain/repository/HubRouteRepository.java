package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteRepository {

    Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);

    Optional<HubRoute> findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId);

    Optional<HubRoute> save(HubRoute saveHubRoute);

    Optional<Page<HubRoute>> findAllByIsDeletedFalse(Pageable pageable);

    Optional<HubRoute> findById(java.util.UUID hubRouteId);
}
