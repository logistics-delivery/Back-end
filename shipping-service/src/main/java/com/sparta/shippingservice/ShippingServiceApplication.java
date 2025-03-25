package com.sparta.shippingservice;

import com.sparta.commonmodule.config.JpaAuditingConfig;
import com.sparta.commonmodule.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sparta")
@EnableFeignClients(basePackages = "com.sparta")
@EnableJpaRepositories(basePackages = "com.sparta")
@EntityScan(basePackages = "com.sparta")

@Import({JpaAuditingConfig.class, SwaggerConfig.class})
public class ShippingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }

}
