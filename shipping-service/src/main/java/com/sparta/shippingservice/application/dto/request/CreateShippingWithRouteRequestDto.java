package com.sparta.shippingservice.application.dto.request;

import jakarta.validation.Valid;

public record CreateShippingWithRouteRequestDto(
        @Valid CreateShippingRequestDto shipping,
        @Valid CreateRouteLogRequestDto routeLog){}
