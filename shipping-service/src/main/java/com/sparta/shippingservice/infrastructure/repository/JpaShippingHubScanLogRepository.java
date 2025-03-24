package com.sparta.shippingservice.infrastructure.repository;

import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShippingHubScanLogRepository extends JpaRepository<ShippingHubScanLog, UUID> {

}
