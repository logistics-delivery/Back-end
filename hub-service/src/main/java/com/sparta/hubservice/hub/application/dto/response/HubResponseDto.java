package com.sparta.hubservice.hub.application.dto.response;

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
public class HubResponseDto {

    private final UUID hubId;
    private final String name;
    private final String address;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final LocalDateTime createdAt;
    private final long createdBy;
    private final LocalDateTime updatedAt;
    private final long updatedBy;

    public HubResponseDto(Hub hub) {
        this.hubId = hub.getHubId();
        this.name = hub.getName();
        this.address = hub.getAddress();
        this.latitude = hub.getLatitude();
        this.longitude = hub.getLongitude();
        this.createdAt = hub.getCreatedAt();
        this.createdBy = hub.getCreatedBy();
        this.updatedAt = hub.getUpdatedAt();
        this.updatedBy = hub.getUpdatedBy();
    }

}
