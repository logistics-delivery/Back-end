package com.sparta.hubservice.hub.application.service.shipping;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;
import com.sparta.hubservice.hub.infrastructure.feignclient.ShippingFeignClient;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusRequestDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusResponseDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusRequestDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ShippingService : call shippingFeignClient")
public class ShippingService {

    private final ShippingFeignClient shippingFeignClient;

    public InboundStatusResponseDto inboundStatus(HubShippingScanLog log){
        // Todo: feign client 호출 응답값에 대한 예외 처리 (common.exception 코드도 추가)
        return shippingFeignClient.inboundStatus(log.getShippingId(), new InboundStatusRequestDto(log));
    }

    public OutboundStatusResponseDto outboundStatus(HubShippingScanLog log) {
        return shippingFeignClient.outboundStatus(log.getShippingId(), new OutboundStatusRequestDto(log));
    }
}
