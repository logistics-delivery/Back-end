package com.sparta.shippingservice.infrastructure.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {
    @GetMapping("/api/v1/hub-route/feign/{from_id}/{to_id}/path")
    HubRouteDetailsResponseDto createPathHubRoute(
            @PathVariable("from_id") UUID fromHubId,
            @PathVariable("to_id") UUID toHubId
    );
}