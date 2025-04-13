package com.sparta.shippingservice.infrastructure.client;

import com.sparta.shippingservice.application.dto.client.ShippingManagerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="shipping-manager-service",url ="${feign.client.config.shipping-manager-service.url}")
public interface ShippingManagerClient {

    @GetMapping("/api/shipping-managers/assign")
    ShippingManagerResponseDto assignManager();


}
