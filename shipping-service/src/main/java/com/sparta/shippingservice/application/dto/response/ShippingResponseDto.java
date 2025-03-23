package com.sparta.shippingservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ShippingResponseDto(
    UUID shippingId,
    String shippingAddress,
    String receiverName,
    UUID shippingManagerId,
    ShippingStatus status
){
    public static ShippingResponseDto from (Shipping shipping) {
        return new ShippingResponseDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getReceiverName(),
                shipping.getShippingManagerId(),
                shipping.getStatus());
    }


}
