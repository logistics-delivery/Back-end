package com.sparta.hubservice.hub.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.hubservice.hub.application.dto.response.HubDeleteResponseDto;
import com.sparta.hubservice.hub.application.dto.request.HubRequestDto;
import com.sparta.hubservice.hub.application.dto.response.HubResponseDto;
import com.sparta.hubservice.hub.application.dto.response.HubUpdateResponseDto;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubQueryRepository;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubService {

    private final GeocodeApiService geocodeApiService;
    private final HubRepository hubRepository;
    private final HubQueryRepository hubQueryRepository;

    // 허브 목록 조회
    @Transactional(readOnly = true)
    public Page<HubResponseDto> getHubs(Pageable pageable) {
        Page<Hub> hubPages = hubRepository.findByIsDeletedFalse(pageable)
            .orElseThrow(ResourceNotFoundException::new);

        return hubPages.map(HubResponseDto::new);
    }

    // 특정 허브 조회
    @Transactional(readOnly = true)
    public HubResponseDto getHub(UUID hubId) {
        Hub hubDetail = hubRepository.findById(hubId).orElseThrow(ResourceNotFoundException::new);
        return new HubResponseDto(hubDetail);
    }

    // 허브 검색
    @Transactional(readOnly = true)
    public Page<HubResponseDto> getSearchHubs(String name, String address, Pageable pageable) {
        Page<Hub> searchHubs = hubQueryRepository.searchByKeyword(name, address, pageable)
            .orElseThrow(ResourceNotFoundException::new);

        return searchHubs.map(HubResponseDto::new);
    }

    // 허브 생성
    @Transactional
    public HubCreateResponseDto createHub(HubRequestDto hubRequestDto, Long userId) {

        // 주소 -> 위,경도값 변환
        Map<String, BigDecimal> map = geocodeApiService.getGeocodeAddress(hubRequestDto.getAddress());

        Hub createHub = Hub.builder()
            .name(hubRequestDto.getName())
            .address(hubRequestDto.getAddress())
            .latitude(map.get("latitude"))
            .longitude(map.get("longitude"))
            .userId(userId)
            .build();

        hubRepository.save(createHub);
        return new HubCreateResponseDto(createHub, "Hub successfully created.");
    }

    // 허브 수정
    @Transactional
    public HubUpdateResponseDto updateHub(UUID hubId, String address, Long userId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(ResourceNotFoundException::new);

        // 주소 -> 위, 경도값 변환
        Map<String, BigDecimal> map = geocodeApiService.getGeocodeAddress(hub.getAddress());

        hub.updateHub(address, map.get("latitude"), map.get("longitude"), userId);
        return new HubUpdateResponseDto(hub,"Hub successfully updated.");
    }

    // 허브삭제 (Soft Delete)
    @Transactional
    public HubDeleteResponseDto deleteHub(UUID hubId, Long userId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(ResourceNotFoundException::new);

        hub.delete(userId);
        return new HubDeleteResponseDto(hub.getHubId(), "Hub successfully deleted.");
    }
}
