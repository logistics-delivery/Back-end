package com.sparta.hubservice.hub.application.dto;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubCreateResponseDto {

    private final UUID hubId;
    private final String message;
    private final LocalDateTime createdAt;
    private final long createdBy;

    public HubCreateResponseDto(Hub hub, String message) {
        this.hubId = getHubId();
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.createdBy = hub.getCreatedBy();
    }

}
