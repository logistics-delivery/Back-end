package com.sparta.product.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-service")
public interface CompanyClient {

    /**
     *  업체 존재 확인
     */
    @GetMapping("/api/v1/companies/{companyId}")
    boolean getCompanyById(@PathVariable("companyId") UUID companyId);
}
