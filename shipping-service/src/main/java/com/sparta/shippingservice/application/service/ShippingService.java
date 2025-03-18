package com.sparta.shippingservice.application.service;

import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.CreateShippingResponseDto;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.repository.ShippingRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;

    public CreateShippingResponseDto create(@Valid CreateShippingRequestDto request){

        Shipping shipping = request.tobe().toShipping();
        shippingRepository.save(shipping);

        return CreateShippingResponseDto.send(shipping);

    }

}
