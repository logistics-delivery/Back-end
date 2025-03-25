package com.sparta.slackservice.infastructure.client;

import com.sparta.slackservice.infastructure.client.dto.SlackNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order-service", path = "/api/v1/orders")
public interface OrderClient {

    @GetMapping("/{id}/slack-info")
    SlackNotificationDto getSlackNotificationInfo(@PathVariable UUID id);
}
