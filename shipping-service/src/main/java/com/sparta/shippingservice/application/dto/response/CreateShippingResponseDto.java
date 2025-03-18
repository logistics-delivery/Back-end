package com.sparta.shippingservice.application.dto.response;

import com.sparta.shippingservice.domain.model.Shipping;

import java.util.UUID;

public record CreateShippingResponseDto(
    UUID shippingId
){
    public static CreateShippingResponseDto send (Shipping shipping) {
        return new CreateShippingResponseDto(shipping.getId());
    }


}
