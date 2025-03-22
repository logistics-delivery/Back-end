package com.sparta.shippingservice.application.service;

import com.sparta.shippingservice.application.dto.request.CreateRouteLogRequestDto;
import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.request.UpdateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingWithRouteResponseDto;
import com.sparta.shippingservice.domain.model.*;
import com.sparta.shippingservice.domain.model.trans.RouteLogSelf;
import com.sparta.shippingservice.domain.repository.ShippingRepository;
import com.sparta.commonmodule.exception.*;

import com.sparta.shippingservice.domain.repository.ShippingRouteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final ShippingRouteRepository shippingRouteRepository;


    @Transactional
    public ShippingWithRouteResponseDto create(@Valid CreateShippingRequestDto request , @Valid CreateRouteLogRequestDto logDto) {
        Shipping shipping = request.of().toShipping();

        RouteLogSelf routeLogSelf = new RouteLogSelf(
                shipping,
                logDto.startHubId(),
                logDto.endHubId(),
                logDto.sequence(),
                logDto.estimatedDistance(),
                logDto.estimatedTime(),
                logDto.actualDistance(),
                logDto.actualTime(),
                logDto.shippingManagerId()

        );
        ShippingRouteLog routeLog = routeLogSelf.toShippingRouteLog();

        // 양방향 연관관계 설정
        routeLog.setShipping(shipping);
        shipping.getRouteLogs().add(routeLog);

        shippingRepository.save(shipping);
        return ShippingWithRouteResponseDto.from(shipping,routeLog);

    }

    @Transactional(readOnly = true)
    public ShippingResponseDto getShippingById(UUID shippingId) {
        Shipping shipping = findShipping(shippingId);
        return ShippingResponseDto.from(shipping);

    }

    @Transactional(readOnly = true)
    public List<ShippingResponseDto> getAllShipping() {
        List<Shipping> result = shippingRepository.findAll();
        return result.stream()
            .map(shipping -> new ShippingResponseDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getReceiverName(),
                shipping.getShippingManagerId(),
                shipping.getStatus()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public ShippingResponseDto updateShipping(UUID shippingId, @Valid UpdateShippingRequestDto request) {
        Shipping shipping = findShipping(shippingId);
        shipping.updateShipping(request.of().toShipping());
        return ShippingResponseDto.from(shipping);

    }

    @Transactional
    public ShippingResponseDto deleteShipping(UUID shippingId, long userId) {
        Shipping shipping = findShipping(shippingId);
        shipping.delete(userId);
        shipping.setStatus(ShippingStatus.CANCELED);
        shippingRepository.save(shipping);
        return ShippingResponseDto.from(shipping);
    }

    @Transactional(readOnly = true)
    public ShippingRouteResponseDto getLogById(UUID shippingId, UUID shippingLogId){
        ShippingRouteLog routeLog = shippingRouteRepository.findByIdAndShippingId(shippingLogId, shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송에 속하지 않는 배송 경로 로그입니다."));
        return ShippingRouteResponseDto.from(routeLog);

    }

    @Transactional(readOnly = true)
    public List<ShippingRouteResponseDto> getLogAll(){
        List<ShippingRouteLog> result = shippingRouteRepository.findAll();
        return result.stream()
                .map(shippingRouteLog->new ShippingRouteResponseDto(
                        shippingRouteLog.getId(),
                        shippingRouteLog.getStartHubId(),
                        shippingRouteLog.getEndHubId(),
                        shippingRouteLog.getSequence(),
                        shippingRouteLog.getEstimatedDistance(),
                        shippingRouteLog.getActualTime(),
                        shippingRouteLog.getActualDistance(),
                        shippingRouteLog.getEstimatedTime(),
                        shippingRouteLog.getShippingManagerId()

                )).collect(Collectors.toList());
    }

    private Shipping findShipping(UUID shippingId) {
        Shipping shipping = shippingRepository.findById(shippingId).orElseThrow(
            () -> new ResourceNotFoundException("찾을 수 없는 배송 정보 입니다."));
        return shipping;
    }

}
