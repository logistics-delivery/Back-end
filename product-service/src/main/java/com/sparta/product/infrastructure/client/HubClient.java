package com.sparta.product.infrastructure.client;

import com.sparta.product.infrastructure.client.dto.response.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {

    /**
     *  허브 단일 조회 (허브 존재 확인)
     */
    @GetMapping("/api/v1/hubs/{hub_id}")
    HubResponseDto getHubById(@PathVariable("hub_id") UUID hubId);
}
