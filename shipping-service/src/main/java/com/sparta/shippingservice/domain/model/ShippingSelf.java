package com.sparta.shippingservice.domain.model;

import java.util.UUID;

public record ShippingSelf(UUID orderId,
                           String shippingAddress,
                           String receiverName,
                           UUID shippingManagerId,
                           ShippingStatus status)  {
    public Shipping toShipping(){
        return new Shipping(
            this.orderId,
            this.shippingAddress,
            this.receiverName,
            this.shippingManagerId,
            this.status
        );
    }

}
