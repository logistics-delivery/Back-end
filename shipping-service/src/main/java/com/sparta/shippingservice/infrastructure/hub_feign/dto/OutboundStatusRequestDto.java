package com.sparta.shippingservice.infrastructure.hub_feign.dto;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;
import com.sparta.hubservice.hub.domain.model.HubShippingScanLog.ShippingStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboundStatusRequestDto {

    private UUID hubId;
    private ShippingStatus shippingStatus;
    private LocalDateTime timestamp;
    private UUID nextHubId;

    public OutboundStatusRequestDto(HubShippingScanLog log) {
        this.hubId = log.getHub().getHubId();
        this.shippingStatus = log.getStatus();
        this.timestamp = log.getTimestamp();
        this.nextHubId = log.getNextHub().getHubId();
    }
}
