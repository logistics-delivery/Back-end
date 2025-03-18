package com.sparta.hubservice.hub.application.service;

import com.sparta.commonmodule.exception.DuplicateResourceException;
import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.application.dto.HubCreateResponseDto;
import com.sparta.hubservice.hub.application.dto.HubDeleteResponseDto;
import com.sparta.hubservice.hub.application.dto.HubRequestDto;
import com.sparta.hubservice.hub.application.dto.HubResponseDto;
import com.sparta.hubservice.hub.application.dto.HubUpdateRequestDto;
import com.sparta.hubservice.hub.application.dto.HubUpdateResponseDto;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.infrastructure.persistence.JPAHubRepository;
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
@Transactional
public class HubService {

    private final JPAHubRepository hubRepository;

    // 허브 목록 조회
    @Transactional(readOnly = true)
    public Page<HubResponseDto> getHubs(Pageable pageable) {
        Page<Hub> hubPages = hubRepository.findByIsDeletedFalse(pageable);
        if (hubPages.isEmpty()) {
            log.error("not found hubs");
            throw new ResourceNotFoundException();
        }
        return hubPages.map(HubResponseDto::new);
    }

    // 특정 허브 조회
    @Transactional(readOnly = true)
    public HubResponseDto getHub(UUID hubId) {
        Hub hubDetail = hubRepository.findById(hubId)
            .orElseThrow(ResourceNotFoundException::new);

        return new HubResponseDto(hubDetail);
    }

    // 허브 생성
    public HubCreateResponseDto createHub(HubRequestDto hubRequestDto, long userId) {
        if(hubRepository.existsByName(hubRequestDto.getName())){
            throw new DuplicateResourceException();
        }
        Hub hub = Hub.builder()
            .name(hubRequestDto.getName())
            .address(hubRequestDto.getAddress())
            .latitude(hubRequestDto.getLatitude())
            .longitude(hubRequestDto.getLongitude())
            .userId(userId)
            .build();

        Hub savedHub = hubRepository.save(hub);
        return new HubCreateResponseDto(savedHub, "Hub successfully created.");
    }

    // 허브 수정
    public HubUpdateResponseDto updateHub(HubUpdateRequestDto requestDto, long userId) {
        Hub hub = hubRepository.findById(requestDto.getHubId())
            .orElseThrow(ResourceNotFoundException::new);

        hub.updateHub(requestDto.getAddress(), requestDto.getLatitude(), requestDto.getLongitude(), userId);
        return new HubUpdateResponseDto(hub, "Hub successfully updated.");
    }

    // 허브삭제 (Soft Delete)
    public HubDeleteResponseDto deleteHub(UUID hubId, long userId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(ResourceNotFoundException::new);

        hub.delete(userId);
        return new HubDeleteResponseDto(hubId, "Hub successfully deleted.");
    }
}
