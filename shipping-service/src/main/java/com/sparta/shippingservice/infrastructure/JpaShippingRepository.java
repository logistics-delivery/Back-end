package com.sparta.shippingservice.infrastructure;

import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.repository.ShippingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaShippingRepository extends JpaRepository<Shipping, UUID>, ShippingRepository {
}
