package com.sparta.hubservice.hub_route.infrastructure.persistence;

import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubRouteRepository extends JpaRepository<HubRoute, UUID>, HubRouteRepository {

}
