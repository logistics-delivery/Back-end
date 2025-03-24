package com.sparta.shippingservice.domain.repository;

import com.sparta.shippingservice.application.dto.request.ShippingRouteSearchCondition;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShippingRouteQueryRepository {
    Page<ShippingRouteLog> search(ShippingRouteSearchCondition condition, Pageable pageable);
}
