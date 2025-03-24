package com.sparta.shippingservice.infrastructure.hub_feign.dto;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog.ShippingStatus;
import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
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
public class OutboundStatusResponseDto {

    private String message;
    private UUID hubId;
    private UUID shippingId;
    private ShippingStatus status;
    private LocalDateTime timestamp;
    private UUID nextHubId;

    public OutboundStatusResponseDto(ShippingHubScanLog log, String message) {
        this.message = message;
        this.hubId = log.getHubId();
        this.shippingId = log.getShipping().getId();
        this.status = ShippingStatus.OUTBOUND;
        this.nextHubId = log.getNextHubId();
    }

}
