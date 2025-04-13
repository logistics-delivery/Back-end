package com.sparta.orderservice.domain.repository;

import com.sparta.orderservice.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID orderId);
    List<Order> findAll();
    void delete(Order order);
}
