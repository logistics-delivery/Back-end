package com.sparta.hubservice.hub.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubUpdateRequestDto {

    private UUID hubId;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;


}
