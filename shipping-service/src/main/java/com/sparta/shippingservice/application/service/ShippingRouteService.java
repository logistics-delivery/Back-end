package com.sparta.shippingservice.application.service;

import com.sparta.shippingservice.application.dto.request.CreateRouteLogRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.domain.model.ShippingRouteLog;
import com.sparta.shippingservice.domain.repository.ShippingRouteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShippingRouteService {
    private final ShippingRouteRepository shippingRouteRepository;


    @Transactional
    public ShippingRouteResponseDto create(@Valid CreateRouteLogRequestDto request) {
        ShippingRouteLog shippingRouteLog = request.of().toShippingRouteLog();
        shippingRouteRepository.save(shippingRouteLog);
        return ShippingRouteResponseDto.from(shippingRouteLog);
    }

}
