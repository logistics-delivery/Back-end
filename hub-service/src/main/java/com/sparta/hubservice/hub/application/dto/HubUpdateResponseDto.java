package com.sparta.hubservice.hub.application.dto;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HubUpdateResponseDto {

    private final UUID hubId;
    private final String message;
    private final String address;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final LocalDateTime updatedAt;
    private final long updatedBy;

    public HubUpdateResponseDto(Hub hub, String message) {
        this.hubId = hub.getHubId();
        this.message = message;
        this.address = hub.getAddress();
        this.latitude = hub.getLatitude();
        this.longitude = hub.getLongitude();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = hub.getCreatedBy();
    }

}
