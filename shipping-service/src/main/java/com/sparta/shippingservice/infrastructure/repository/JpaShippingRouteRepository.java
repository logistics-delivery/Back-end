package com.sparta.shippingservice.infrastructure.repository;

import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import com.sparta.shippingservice.domain.repository.ShippingRouteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaShippingRouteRepository extends JpaRepository<ShippingRouteLog, UUID>, ShippingRouteRepository {
}
