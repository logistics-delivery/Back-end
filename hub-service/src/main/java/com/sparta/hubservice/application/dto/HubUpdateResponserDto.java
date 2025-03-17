package com.sparta.hubservice.application.dto;

import com.sparta.hubservice.domain.model.Hub;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubUpdateResponserDto {

    private final UUID hubId;
    private final String message;
    private final String location;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final LocalDateTime updatedAt;
    private final long updatedBy;

    public HubUpdateResponserDto(Hub hub, String message) {
        this.hubId = getHubId();
        this.message = message;
        this.location = getLocation();
        this.latitude = getLatitude();
        this.longitude = getLongitude();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = hub.getCreatedBy();
    }

}
