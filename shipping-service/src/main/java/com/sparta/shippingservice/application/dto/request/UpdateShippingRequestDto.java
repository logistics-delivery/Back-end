package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.ShippingSelf;
import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.Optional;
import java.util.UUID;


public record UpdateShippingRequestDto(
    UUID orderId,
    String shippingAddress,
    String receiverName,
    UUID shippingManagerId,
    ShippingStatus status
) {
    public ShippingSelf of(){
        return new ShippingSelf(
                this.orderId(),
                this.shippingAddress(),
                this.receiverName(),
                this.shippingManagerId(),
                this.status()

        );
    }
}

