package com.sparta.hubservice.hub.presentation.controller;

import com.sparta.hubservice.hub.application.service.HubShippingScanService;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusResponseDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipping-scan")
public class HubShippingScanController {

    private final HubShippingScanService hubShippingScanService;

    // 입고 처리 저장
    @PostMapping("/{hub_id}/{shipping_id}/inbound-log")
    public ResponseEntity<InboundStatusResponseDto> inboundStatus(
        @PathVariable("hub_id") UUID hubId,
        @PathVariable("shipping_id") UUID shippingId,
        @RequestParam("user_id") Long userId){

        InboundStatusResponseDto responseDto =
            hubShippingScanService.createInbound(hubId, shippingId, userId);

        return ResponseEntity.ok(responseDto);
    }

    // 출고 처리 저장
    @PostMapping("/{hub_id}/{shipping_id}/outbound-log")
    public ResponseEntity<OutboundStatusResponseDto> outboundStatus(
        @PathVariable("hub_id") UUID hubId,
        @PathVariable("shipping_id") UUID shippingId,
        @RequestParam("user_id") Long userId
    ){
        OutboundStatusResponseDto responseDto =
            hubShippingScanService.createOutbound(hubId, shippingId, userId);

        return ResponseEntity.ok(responseDto);
    }

}
