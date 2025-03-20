package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository {

    Optional<HubRoute> findByFromHubIdAndToHubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);
}
