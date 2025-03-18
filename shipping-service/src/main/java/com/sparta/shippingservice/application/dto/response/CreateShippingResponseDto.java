package com.sparta.shippingservice.application.dto.response;

import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.UUID;

public record CreateShippingResponseDto(
    UUID shippingId,
    String shippingAddress,
    String receiverName,
    UUID shippingManagerId,
    ShippingStatus status
){
    public static CreateShippingResponseDto send (Shipping shipping) {
        return new CreateShippingResponseDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getReceiverName(),
                shipping.getShippingManagerId(),
                shipping.getStatus());
    }


}
