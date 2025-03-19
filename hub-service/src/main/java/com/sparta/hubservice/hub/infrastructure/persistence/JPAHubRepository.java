package com.sparta.hubservice.hub.infrastructure.persistence;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubRepository extends JpaRepository<Hub, UUID>, HubRepository {
}
