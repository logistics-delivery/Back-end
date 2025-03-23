package com.sparta.orderservice.infrastructure.client;

import com.sparta.orderservice.infrastructure.client.dto.response.DecreaseProductQuantityResponseDto;
import com.sparta.orderservice.infrastructure.client.dto.request.DecreaseProductQuantityServiceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

    @FeignClient(name = "product-service", path = "/api/v1/products")
    public interface ProductClient {

        @PutMapping("/{productId}/decrease")
        DecreaseProductQuantityResponseDto decreaseProductQuantity(
                @PathVariable("productId") UUID productId,
                @RequestBody DecreaseProductQuantityServiceRequestDto requestDto
        );
    }
