package com.sparta.hubservice.hub.infrastructure.feignclient.dto;

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
    private LocalDateTime timestamp;
    private UUID nextHubId;

}
