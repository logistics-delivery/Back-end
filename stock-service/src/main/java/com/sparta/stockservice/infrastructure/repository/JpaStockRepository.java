package com.sparta.stockservice.infrastructure.repository;

import com.sparta.stockservice.domain.model.Stock;
import com.sparta.stockservice.domain.repository.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStockRepository extends StockRepository, JpaRepository<Stock, UUID> {
}
