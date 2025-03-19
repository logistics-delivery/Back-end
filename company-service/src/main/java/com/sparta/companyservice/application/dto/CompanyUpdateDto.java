package com.sparta.companyservice.application.dto;

import java.util.UUID;

public record CompanyUpdateDto(
        String name,
        UUID hubId,
        String address
) {}
