package com.sparta.orderservice;

import com.sparta.commonmodule.config.JpaAuditingConfig;
import com.sparta.commonmodule.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients(basePackages = "com.sparta.orderservice.infrastructure.client")
@SpringBootApplication(scanBasePackages = "com.sparta")
@Import({SwaggerConfig.class, JpaAuditingConfig.class})
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
