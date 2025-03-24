package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.service.ShippingHubScanService;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusRequestDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shippings")
public class ShippingHubScanController {

    private final ShippingHubScanService shippingHubScanService;

    // feign client : 배송건에 대한 허브 입고 기록 저장
    @PostMapping("/{shipping_id}/inbound")
    public ResponseEntity<InboundStatusResponseDto> inbound(
        @PathVariable("shipping_id") UUID shippingId,
        @RequestHeader("user_id") Long userId,
        @RequestBody InboundStatusRequestDto inboundStatusRequestDto) {

        InboundStatusResponseDto responseDto =
            shippingHubScanService.createInboundLog(shippingId,userId, inboundStatusRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
