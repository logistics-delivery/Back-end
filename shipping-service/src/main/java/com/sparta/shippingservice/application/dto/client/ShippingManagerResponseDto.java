package com.sparta.shippingservice.application.dto.client;

import com.sparta.shippingmanager.domain.model.ManagerType;

import java.util.UUID;

public record ShippingManagerResponseDto(
        UUID id,
        String name,
        ManagerType managerType
) {}
