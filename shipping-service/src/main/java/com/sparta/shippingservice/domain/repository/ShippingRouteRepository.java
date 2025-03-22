package com.sparta.shippingservice.domain.repository;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShippingRouteRepository {
    ShippingRouteLog save(ShippingRouteLog  shippingRouteLog);
    Optional<ShippingRouteLog> findById(UUID id);
    List<ShippingRouteLog> findAll();
    Optional<ShippingRouteLog> findByIdAndShippingId(UUID id, UUID shippingId);

}
