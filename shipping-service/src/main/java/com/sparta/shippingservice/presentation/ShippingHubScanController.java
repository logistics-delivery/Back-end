package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.service.ShippingHubScanService;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusRequestDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusResponseDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.OutboundStatusRequestDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.OutboundStatusResponseDto;
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
    public ResponseEntity<InboundStatusResponseDto> inboundStatus(
        @PathVariable("shipping_id") UUID shippingId,
        @RequestHeader("user_id") Long userId,
        @RequestBody InboundStatusRequestDto inboundStatusRequestDto) {

        InboundStatusResponseDto responseDto =
            shippingHubScanService.createInboundLog(shippingId,userId, inboundStatusRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // feign client : 배송건에 대한 허브 출고 기록 저장
    @PostMapping("/{shipping_id}/outbound")
    public ResponseEntity<OutboundStatusResponseDto> outboundStatus(
        @PathVariable("shipping_id") UUID shippingId,
        @RequestHeader("user_id") Long userId,
        @RequestBody OutboundStatusRequestDto outboundStatusRequestDto){

        OutboundStatusResponseDto responseDto =
            shippingHubScanService.createOutboundLog(shippingId, userId, outboundStatusRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
