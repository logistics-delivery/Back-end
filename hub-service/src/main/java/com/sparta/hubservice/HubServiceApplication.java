package com.sparta.hubservice;

import com.sparta.commonmodule.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.sparta")
@EnableFeignClients
@Import(SwaggerConfig.class)
public class HubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubServiceApplication.class, args);
    }

}
