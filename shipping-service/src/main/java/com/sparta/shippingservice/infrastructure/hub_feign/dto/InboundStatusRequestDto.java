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
public class InboundStatusRequestDto {

    private UUID hubId;
    private ShippingStatus status;
    private LocalDateTime timestamp;


}
