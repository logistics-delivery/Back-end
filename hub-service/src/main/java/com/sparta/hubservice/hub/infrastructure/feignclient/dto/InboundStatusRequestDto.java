package com.sparta.hubservice.hub.infrastructure.feignclient.dto;

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
public class InboundStatusRequestDto {

    private UUID hubId;
    private ShippingStatus status;
    private LocalDateTime timestamp;

    public InboundStatusRequestDto(HubShippingScanLog hubShippingScanLog) {
        this.hubId = hubShippingScanLog.getHub().getHubId();
        this.status = hubShippingScanLog.getStatus();
        this.timestamp = hubShippingScanLog.getTimestamp();
    }

}
