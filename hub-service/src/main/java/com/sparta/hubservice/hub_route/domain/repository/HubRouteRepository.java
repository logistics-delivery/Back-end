package com.sparta.hubservice.hub_route.domain.repository;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteRepository {

    Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);

    HubRoute findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId);

    HubRoute save(HubRoute saveHubRoute);

    Page<HubRoute> findAllByIsDeletedFalse(Pageable pageable);

    Optional<HubRoute> findById(UUID hubRouteId);
}
