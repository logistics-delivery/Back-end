package com.sparta.stockservice.domain.repository;

import com.sparta.stockservice.domain.model.Stock;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository {

    Optional<Stock> findByProductIdAndHubId(UUID productId, UUID hubId);
}
