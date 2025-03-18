package com.sparta.shippingservice.domain.repository;


import com.sparta.shippingservice.domain.model.Shipping;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShippingRepository {
    Shipping save(Shipping shipping);
    Optional<Shipping> findById(UUID id);
}
