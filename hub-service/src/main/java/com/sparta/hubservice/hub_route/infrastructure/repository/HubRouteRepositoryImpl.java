package com.sparta.hubservice.hub_route.infrastructure.repository;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import com.sparta.hubservice.hub_route.infrastructure.persistence.JPAHubRouteRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRouteRepositoryImpl implements HubRouteRepository {

    private final JPAHubRouteRepository jpaHubRouteRepository;

    @Override
    public Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId,
        UUID toHubId) {
        return jpaHubRouteRepository.findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(fromHubId, toHubId);
    }

    @Override
    public Optional<HubRoute> findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId) {
        return jpaHubRouteRepository.findByHubRouteIdAndIsDeletedFalse(hubRouteId);
    }

    @Override
    public HubRoute save(HubRoute hubRoute) {
        return jpaHubRouteRepository.save(hubRoute);
    }

    @Override
    public Optional<Page<HubRoute>> findAllByIsDeletedFalse(Pageable pageable) {
        return jpaHubRouteRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public Optional<HubRoute> findById(UUID hubRouteId) {
        return jpaHubRouteRepository.findById(hubRouteId);
    }

    @Override
    public Optional<List<HubRoute>> findByFromHub(Hub hub) {
        return jpaHubRouteRepository.findByFromHub(hub);
    }

    @Override
    public Optional<HubRoute> findByFromHubAndToHub(Hub fromHub, Hub toHub) {
        return jpaHubRouteRepository.findByFromHubAndToHub(fromHub, toHub);
    }
}
