package com.sparta.hubservice.hub.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubDeleteResponseDto {

    private final UUID hubId;
    private final String message;

    public HubDeleteResponseDto(UUID hubId, String message) {
        this.hubId = hubId;
        this.message = message;
    }

}
