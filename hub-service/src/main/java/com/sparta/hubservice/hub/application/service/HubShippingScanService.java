package com.sparta.hubservice.hub.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.application.service.shipping.ShippingService;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub.domain.repository.HubShippingScanRepository;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.InboundStatusResponseDto;
import com.sparta.hubservice.hub.infrastructure.feignclient.dto.OutboundStatusResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j(topic = "HubShippingScanService")
public class HubShippingScanService {

    private final HubShippingScanRepository hubShippingScanRepository;
    private final HubRepository hubRepository;
    private final ShippingService shippingService;

    @Transactional
    public InboundStatusResponseDto createInbound(UUID hubId, UUID shippingId, Long userId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(ResourceNotFoundException::new);

        HubShippingScanLog hubShippingScanLog =
            HubShippingScanLog.createInboundLog(hub, shippingId, userId);

        hubShippingScanRepository.save(hubShippingScanLog);
        log.info("Success Save InboundLog - HubShippingScanLog: {}", hubShippingScanLog);

        return shippingService.inboundStatus(hubShippingScanLog);
    }

    @Transactional
    public OutboundStatusResponseDto createOutbound(UUID hubId, UUID shippingId, UUID nextHubId, Long userId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(ResourceNotFoundException::new);
        Hub nextHub = hubRepository.findById(nextHubId).orElseThrow(ResourceNotFoundException::new);

        HubShippingScanLog hubShippingScanLog =
            HubShippingScanLog.createOutboundLog(hub, shippingId, nextHub, userId);

        hubShippingScanRepository.save(hubShippingScanLog);
        log.info("Success Save OutboundLog - HubShippingScanLog: {}", hubShippingScanLog);

        return shippingService.outboundStatus(hubShippingScanLog);
    }
}
