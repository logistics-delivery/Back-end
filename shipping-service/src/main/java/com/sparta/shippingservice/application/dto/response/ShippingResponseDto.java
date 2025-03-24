package com.sparta.shippingservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;

import java.util.UUID;




public record ShippingResponseDto(
    UUID shippingId,
    String receiverName,
    String shippingAddress,
    ShippingStatus status
){
    public static ShippingResponseDto from (Shipping shipping) {
        return new ShippingResponseDto(
                shipping.getId(),
                shipping.getReceiverName(),
                shipping.getShippingAddress(),
                shipping.getStatus());
    }


}
