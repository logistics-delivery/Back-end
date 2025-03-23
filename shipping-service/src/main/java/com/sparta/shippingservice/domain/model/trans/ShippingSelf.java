package com.sparta.shippingservice.domain.model.trans;

import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.UUID;

public record ShippingSelf(UUID orderId,
                           String shippingAddress,
                           String receiverName,
                           UUID shippingManagerId,
                           ShippingStatus status)  {
    public Shipping toShipping(Long userId){ // DTO - > entity 전환
        return new Shipping(
                userId,
            this.orderId,
            this.shippingAddress,
            this.receiverName,
            this.shippingManagerId,
            this.status
        );
    }

}
