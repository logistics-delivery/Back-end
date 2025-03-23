package com.sparta.orderservice.domain.repository;

import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.model.OrderStatus;

import java.util.List;

public interface OrderQueryDSLRepository {
    List<Order> searchOrders(String name, OrderStatus status);
}