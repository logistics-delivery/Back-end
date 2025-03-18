package com.sparta.hubservice.application.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRequestDto {

    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;


}
