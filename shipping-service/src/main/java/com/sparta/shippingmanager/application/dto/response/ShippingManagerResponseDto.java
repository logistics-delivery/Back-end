package com.sparta.shippingmanager.application.dto.response;

import com.sparta.shippingmanager.domain.model.ManagerType;
import com.sparta.shippingmanager.domain.model.ShippingManager;

import java.util.UUID;

public record ShippingManagerResponseDto(
        UUID id,
        ManagerType managerType
) {
    public static ShippingManagerResponseDto from(ShippingManager manager) {
        return new ShippingManagerResponseDto(
                manager.getId(),
                manager.getManagerType()
        );
    }
}