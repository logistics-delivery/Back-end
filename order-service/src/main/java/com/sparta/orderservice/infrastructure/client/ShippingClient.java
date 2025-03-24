package com.sparta.orderservice.infrastructure.client;

import com.sparta.orderservice.infrastructure.client.dto.request.CreateShippingRequestDto;
import com.sparta.orderservice.infrastructure.client.dto.response.CreateShippingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service", path = "/api/v1/shippings")
public interface ShippingClient {

    @PostMapping
    CreateShippingResponseDto createShipping(@RequestBody CreateShippingRequestDto request);
}
