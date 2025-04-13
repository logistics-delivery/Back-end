package com.sparta.shippingservice.infrastructure.hub_feign.dto;

import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
import com.sparta.shippingservice.domain.model.ShippingHubScanLog.ShippingStatus;
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
public class InboundStatusResponseDto {

    private String message;
    private UUID hubId;
    private UUID shippingId;
    private ShippingStatus shippingStatus;
    private LocalDateTime timestamp;

    public InboundStatusResponseDto(ShippingHubScanLog log, String message) {
        this.shippingStatus = ShippingStatus.INBOUND;
        this.hubId =  log.getHubId();
        this.shippingId = log.getShipping().getId();
        this.message = message;
    }


}
