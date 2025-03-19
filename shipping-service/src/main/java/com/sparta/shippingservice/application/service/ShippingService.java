package com.sparta.shippingservice.application.service;

import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.repository.ShippingRepository;
import com.sparta.commonmodule.exception.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;


    @Transactional
    public ShippingResponseDto create(@Valid CreateShippingRequestDto request){

        Shipping shipping = request.tobe().toShipping();
        shippingRepository.save(shipping); //DB 저장

        return ShippingResponseDto.send(shipping);
    }

    @Transactional(readOnly = true)
    public ShippingResponseDto getShippingById(UUID shippingId){
        Shipping shipping = shippingRepository.findById(shippingId).orElseThrow(
            () -> new ResourceNotFoundException("찾을 수 없는 배송 정보 입니다.")
        );
        return ShippingResponseDto.send(shipping);

    }





}
