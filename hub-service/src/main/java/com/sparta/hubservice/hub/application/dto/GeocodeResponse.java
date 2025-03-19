package com.sparta.hubservice.hub.application.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class GeocodeResponse {

    private Address address;

    @Data
    public static class Address {
        private String x; // longitude
        private String y; // latitude
    }


}
