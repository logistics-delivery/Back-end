package com.sparta.shippingservice.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
import com.sparta.shippingservice.domain.repository.ShippingHubScanLogRepository;
import com.sparta.shippingservice.domain.repository.ShippingRepository;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusRequestDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.InboundStatusResponseDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.OutboundStatusRequestDto;
import com.sparta.shippingservice.infrastructure.hub_feign.dto.OutboundStatusResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShippingHubScanService {

    private final ShippingRepository shippingRepository;
    private final ShippingHubScanLogRepository shippingHubScanLogRepository;

    // 입고 처리
    @Transactional
    public InboundStatusResponseDto createInboundLog(UUID shippingId, Long userId, InboundStatusRequestDto requestDto) {

        Shipping shipping = shippingRepository.findById(shippingId)
            .orElseThrow(ResourceNotFoundException::new);

        ShippingHubScanLog log =
        ShippingHubScanLog.createInboundLog(requestDto.getHubId(), shipping, userId);

        shippingHubScanLogRepository.save(log);

        return new InboundStatusResponseDto(log, "Success Save Inbound Log");
    }

    @Transactional
    public OutboundStatusResponseDto createOutboundLog(UUID shippingId, Long userId, OutboundStatusRequestDto outboundStatusRequestDto) {

        Shipping shipping = shippingRepository.findById(shippingId)
            .orElseThrow(ResourceNotFoundException::new);

        ShippingHubScanLog log =
            ShippingHubScanLog.createOutboundLog(outboundStatusRequestDto.getHubId(), shipping, userId);

        shippingHubScanLogRepository.save(log);

        return new OutboundStatusResponseDto(log, "Success Save Outbound Log");
    }
}
