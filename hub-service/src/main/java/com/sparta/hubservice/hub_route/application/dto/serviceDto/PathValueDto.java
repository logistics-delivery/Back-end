package com.sparta.hubservice.hub_route.application.dto.serviceDto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PathValueDto {

    private BigDecimal totalDistance;
    private int totalDuration;

}
