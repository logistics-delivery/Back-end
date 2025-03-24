package com.sparta.shippingservice.infrastructure.repository;

import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
import com.sparta.shippingservice.domain.repository.ShippingHubScanLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShippingHubScanLogRepositoryImpl implements ShippingHubScanLogRepository {

    private final JpaShippingHubScanLogRepository jpaRepository;

    @Override
    public void save(ShippingHubScanLog log) {
        jpaRepository.save(log);
    }
}
