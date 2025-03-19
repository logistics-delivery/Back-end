package com.sparta.hubservice.hub.application.dto;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class HubCreateResponseDto {

    private final UUID hubId;
    private final String message;
    private final String name;
    private final String address;
    private final BigDecimal latitud;
    private final BigDecimal longitud;
    private final LocalDateTime createdAt;
    private final long createdBy;

    public HubCreateResponseDto(Hub hub, String message) {
        this.hubId = hub.getHubId();
        this.message = message;
        this.name = hub.getName();
        this.address = hub.getAddress();
        this.latitud = hub.getLatitude();
        this.longitud = hub.getLongitude();
        this.createdAt = LocalDateTime.now();
        this.createdBy = hub.getCreatedBy();
    }

}
