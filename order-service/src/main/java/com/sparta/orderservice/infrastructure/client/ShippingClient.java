package com.sparta.orderservice.infrastructure.client;

import com.sparta.orderservice.infrastructure.client.dto.request.CreateShippingRequestDto;
import com.sparta.orderservice.infrastructure.client.dto.response.CreateShippingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "shipping-service", path = "/api/v1/shippings")
public interface ShippingClient {

    // 배송 생성
    @PostMapping
    CreateShippingResponseDto createShipping(@RequestBody CreateShippingRequestDto request);

    // 주문 ID로 배송 조회
    @GetMapping("/{orderId}")
    CreateShippingResponseDto getShippingInfo(@PathVariable("orderId") UUID orderId);
}
