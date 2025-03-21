package com.sparta.hubservice.hub.application.dto.response;

import java.util.List;
import lombok.Data;
import org.springframework.data.jpa.repository.query.Meta;

@Data
public class GeocodeResponse {
    private String status;
    private Meta meta;
    private List<Address> addresses;
    private String errorMessage;

    @Data
    public static class Address {
        private String x; // longitude
        private String y; // latitude
    }


}
