package com.sparta.hubservice.application.service;

import com.sparta.commonmodule.exception.DuplicateResourceException;
import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.application.dto.HubCreateResponseDto;
import com.sparta.hubservice.application.dto.HubDeleteResponseDto;
import com.sparta.hubservice.application.dto.HubRequestDto;
import com.sparta.hubservice.application.dto.HubResponseDto;
import com.sparta.hubservice.application.dto.HubUpdateRequestDto;
import com.sparta.hubservice.application.dto.HubUpdateResponseDto;
import com.sparta.hubservice.domain.model.Hub;
import com.sparta.hubservice.domain.repository.HubRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubService {

    private final HubRepository hubRepository;

    // 허브 목록 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Page<HubResponseDto>> getHubs(Pageable pageable) {
        Page<HubResponseDto> hubResponseDtos = hubRepository.findAllAndIsDeletedFalse(pageable);
        if (hubResponseDtos.isEmpty()) {
            log.error("not found hubs");
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(hubResponseDtos, HttpStatus.OK);
    }

    // 특정 허브 조회
    @Transactional(readOnly = true)
    public ResponseEntity<HubResponseDto> getHub(UUID hubId) {
        Hub hubDetail = hubRepository.findById(hubId)
            .orElseThrow(ResourceNotFoundException::new);

        return ResponseEntity.ok(new HubResponseDto(hubDetail));
    }

    // 허브 생성
    @Transactional
    public ResponseEntity<HubCreateResponseDto> createHub(HubRequestDto hubRequestDto, long userId) {
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
        return ResponseEntity.ok(new HubCreateResponseDto(savedHub, "Hub successfully created."));
    }

    // 허브 수정
    @Transactional
    public ResponseEntity<HubUpdateResponseDto> updateHub(HubUpdateRequestDto requestDto, long userId) {
        Hub hub = hubRepository.findById(requestDto.getHubId())
            .orElseThrow(ResourceNotFoundException::new);

        hub.updateHub(requestDto.getAddress(), requestDto.getLatitude(), requestDto.getLongitude(), userId);
        return ResponseEntity.ok(new HubUpdateResponseDto(hub, "Hub successfully updated."));
    }

    // 허브삭제 (Soft Delete)
    @Transactional
    public ResponseEntity<HubDeleteResponseDto> deleteHub(UUID hubId, long userId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(ResourceNotFoundException::new);

        hub.delete(userId);
        return ResponseEntity.ok(new HubDeleteResponseDto(hubId, "Hub successfully deleted."));
    }
}
