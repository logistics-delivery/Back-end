package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.Optional;
import java.util.UUID;


public record UpdateShippingRequestDto(
    Optional<UUID> orderId,
    Optional<String> shippingAddress,
    Optional<String> receiverName,
    Optional<UUID> shippingManagerId,
    Optional<ShippingStatus> status
) {
    public static UpdateShippingRequestDto of(
        UUID orderId,
        String shippingAddress,
        String receiverName,
        UUID shippingManagerId,
        ShippingStatus status
    ) {
        return new UpdateShippingRequestDto(
            Optional.ofNullable(orderId),
            Optional.ofNullable(shippingAddress),
            Optional.ofNullable(receiverName),
            Optional.ofNullable(shippingManagerId),
            Optional.ofNullable(status)
        );
    }
}

