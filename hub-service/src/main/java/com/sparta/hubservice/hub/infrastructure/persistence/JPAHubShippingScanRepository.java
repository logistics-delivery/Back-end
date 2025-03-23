package com.sparta.hubservice.hub.infrastructure.persistence;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAHubShippingScanRepository extends JpaRepository<HubShippingScanLog, UUID> {

}
