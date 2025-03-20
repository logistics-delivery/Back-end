package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository {

    Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);

    HubRoute findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId);
}
