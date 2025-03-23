package com.sparta.hubservice.hub.infrastructure.feignclient;

import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusRequestDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusResponseDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusRequestDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="shipping-service", url="${shipping.service.url}")
public interface ShippingFeignClient {

    // 입고 처리 내용 전달
    @PostMapping("/{shipping_id}/inbound")
    InboundStatusResponseDto inboundStatus(
        @PathVariable("shipping_id") UUID shippingId,
        @RequestBody InboundStatusRequestDto inboundStatusRequestDto);

    // 출고 처리 내용 전달
    @PostMapping("/{shipping_id}/outbound")
    OutboundStatusResponseDto outboundStatus(
        @PathVariable("shipping_id") UUID shippingId,
        @RequestBody OutboundStatusRequestDto outboundStatusRequestDto);
}