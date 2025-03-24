package com.sparta.hubservice.hub_route.infrastructure.persistence;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import feign.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubRouteRepository extends JpaRepository<HubRoute, UUID> {

    Optional<HubRoute> findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(UUID fromHubId, UUID toHubId);

    Optional<HubRoute> findByHubRouteIdAndIsDeletedFalse(UUID hubRouteId);

    Optional<Page<HubRoute>> findAllByIsDeletedFalse(Pageable pageable);

    Optional<List<HubRoute>> findByFromHub(Hub hub);

    @Query("SELECT h FROM HubRoute h WHERE h.fromHub = :from AND h.toHub = :to AND h.isDeleted = false ORDER BY h.distance ASC LIMIT 1")
    Optional<HubRoute> findShortestRouteByFromAndTo(@Param("from") Hub from, @Param("to") Hub to);
}
