package com.sparta.companyservice;

import com.sparta.commonmodule.config.JpaAuditingConfig;
import com.sparta.commonmodule.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.sparta")
@EnableFeignClients(basePackages = "com.sparta")
@Import({SwaggerConfig.class, JpaAuditingConfig.class})
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }

}
