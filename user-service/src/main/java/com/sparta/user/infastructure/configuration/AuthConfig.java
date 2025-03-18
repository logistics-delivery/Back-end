package com.sparta.user.infastructure.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AuthConfig {
    @Value("${service.master.signup-key}")
    private String MasterKey;
    @Value("${service.hub.signup-key}")
    private String HubKey;
    @Value("${service.shipping.signup-key}")
    private String ShippingKey;
}
