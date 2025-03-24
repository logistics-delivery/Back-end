package com.sparta.shippingservice.infrastructure.hub_feign.dto;

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
public class OutboundStatusResponseDto {

    private String message;
    private UUID hubId;
    private UUID shippingId;
    private ShippingStatus shippingStatus;
    private LocalDateTime timestamp;
    private UUID nextHubId;

}
