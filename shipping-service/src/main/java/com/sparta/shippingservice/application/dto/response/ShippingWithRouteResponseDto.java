package com.sparta.shippingservice.application.dto.response;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import java.util.UUID;
public record ShippingWithRouteResponseDto(
        UUID shippingId,
        String shippingAddress,
        String receiverName,
        UUID shippingManagerId,
        String status,
        ShippingRouteResponseDto route
) {
    public static ShippingWithRouteResponseDto from(Shipping shipping, ShippingRouteLog routeLog) {
        ShippingRouteResponseDto routeDto = ShippingRouteResponseDto.from(routeLog);
        return new ShippingWithRouteResponseDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getReceiverName(),
                shipping.getShippingManagerId(),
                shipping.getStatus().name(),
                routeDto
        );
    }
}