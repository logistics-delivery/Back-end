package com.sparta.shippingservice.infrastructure.hub_feign.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.shippingservice.domain.model.ShippingHubScanLog;
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
    private ShippingHubScanLog.ShippingStatus status;
    private LocalDateTime timestamp;
    private UUID nextHubId;

}
