package com.sparta.shippingservice.application.service;

import com.sparta.shippingmanager.domain.model.ManagerType;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingservice.application.dto.client.ShippingManagerResponseDto;
import com.sparta.shippingservice.application.dto.request.*;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingSearchResult;
import com.sparta.shippingservice.application.dto.response.ShippingWithRouteResponseDto;
import com.sparta.shippingservice.domain.model.*;
import com.sparta.shippingservice.domain.repository.ShippingRepository;
import com.sparta.commonmodule.exception.*;

import com.sparta.shippingservice.domain.repository.ShippingRouteQueryRepository;
import com.sparta.shippingservice.domain.repository.ShippingRouteRepository;
import com.sparta.shippingservice.infrastructure.client.HubClient;
import com.sparta.shippingservice.infrastructure.client.HubRouteDetailsResponseDto;
import com.sparta.shippingservice.infrastructure.client.ShippingManagerClient;
import com.sparta.shippingservice.infrastructure.repository.ShippingSearchRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ShippingManagerClient shippingManagerClient;
    private final ShippingSearchRepository searchRepository;
    private final HubClient hubClient;
    private final ShippingRouteQueryRepository shippingRouteQueryRepository;

//각 허브에 10명 / 업체에 10명

    @Transactional
    public ShippingWithRouteResponseDto create(@Valid CreateShippingRequestDto request ,Long userId) {

        ShippingManagerResponseDto manager = shippingManagerClient.assignManager();
        if(manager.managerType() != ManagerType.CARRIER){
            throw new InvalidParameterException("배송 담당자는 업체 소속이어야 합니다.");
        }

        Shipping shipping = request.of(manager.id()).toShipping(userId);

        HubRouteDetailsResponseDto pathHubRoute = hubClient.createPathHubRoute(shipping.getRouteLog().getFromHubId(),shipping.getRouteLog().getToHubId());
        ShippingRouteLog routeLog = pathHubRoute.toShippingRouteLog();

        // 양방향 연관관계 설정
        routeLog.setShipping(shipping);
        shipping.add(routeLog);

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
                shipping.getStatus()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public ShippingResponseDto updateShipping(UUID shippingId, @Valid UpdateShippingRequestDto request ,Long userId) {
        Shipping shipping = findShipping(shippingId);
        shipping.updateShipping(request.of().toShipping(userId),userId);
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

    public Page<ShippingResponseDto> searchShipping(ShippingSearchCondition condition) {
        ShippingSearchResult result = searchRepository.search(condition);
        return new PageImpl<>(
                result.getContent(),
                PageRequest.of(result.getPage(), result.getPageSize()),
                result.getTotalCount()
        );
    }

    @Transactional(readOnly = true)
    public ShippingRouteResponseDto getLogById(UUID shippingId, UUID shippingLogId){
        ShippingRouteLog routeLog = shippingRouteRepository.findByIdAndShippingId(shippingLogId, shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송에 속하지 않는 배송 경로 로그입니다."));
        return ShippingRouteResponseDto.from(routeLog); // 예외 컨트롤러 단에서 잡기

    }

    @Transactional(readOnly = true)
    public List<ShippingRouteResponseDto> getLogAll() {
        List<ShippingRouteLog> result = shippingRouteRepository.findAll();
        return result.stream()
                .map(ShippingRouteResponseDto::from)
                .collect(Collectors.toList());
    }


    @Transactional
    public ShippingRouteResponseDto deleteShippingLog(UUID shippingId,UUID shippingLogId, long userId) {
        ShippingRouteLog routeLog = shippingRouteRepository.findByIdAndShippingId(shippingLogId, shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송에 속하지 않는 배송 경로 로그입니다."));

        routeLog.delete(userId);
        shippingRouteRepository.save(routeLog);
        return ShippingRouteResponseDto.from(routeLog);
    }



    private Shipping findShipping(UUID shippingId) {
        Shipping shipping = shippingRepository.findById(shippingId).orElseThrow(
            () -> new ResourceNotFoundException("찾을 수 없는 배송 정보 입니다."));
        return shipping;
    }

    @Transactional(readOnly = true) // 배송 로그 검색
    public Page<ShippingRouteResponseDto> searchShippingLogs(ShippingRouteSearchCondition condition, Pageable pageable) {
        Page<ShippingRouteLog> logs = shippingRouteQueryRepository.search(condition, pageable);
        return logs.map(ShippingRouteResponseDto::from);
    }

}
