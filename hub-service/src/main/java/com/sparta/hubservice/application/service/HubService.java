package com.sparta.hubservice.application.service;

import com.sparta.commonmodule.exception.DuplicateResourceException;
import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.application.dto.HubCreateResponseDto;
import com.sparta.hubservice.application.dto.HubRequestDto;
import com.sparta.hubservice.application.dto.HubResponseDto;
import com.sparta.hubservice.application.dto.HubUpdateRequestDto;
import com.sparta.hubservice.application.dto.HubUpdateResponserDto;
import com.sparta.hubservice.domain.model.Hub;
import com.sparta.hubservice.domain.repository.HubRepository;
import java.util.Optional;
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

    // 특정 허브 목록 조회
    @Transactional(readOnly = true)
    public ResponseEntity<HubResponseDto> getHub(UUID hubId) {
        Optional<Hub> hubDetail = hubRepository.findById(hubId);
        if(hubDetail.isEmpty()){
            throw new ResourceNotFoundException();
        }
        HubResponseDto responseDto = new HubResponseDto(hubDetail.get());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 허브 생성
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<HubCreateResponseDto> createHub(HubRequestDto hubRequestDto, long userId) {
        try{
            Hub savedHub = Hub.builder()
                .name(hubRequestDto.getName())
                .address(hubRequestDto.getAddress())
                .latitude(hubRequestDto.getLatitude())
                .longitude(hubRequestDto.getLongitude())
                .userId(userId)
                .build();
            savedHub = hubRepository.save(savedHub);
            HubCreateResponseDto responseDto = new HubCreateResponseDto(savedHub, "Hub successfully created.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        catch (DuplicateResourceException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest()
                .body(new HubCreateResponseDto(null, "Failed to create hub : Duplicate name"));
        }
        catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest()
                .body(new HubCreateResponseDto(null, "Failed to create hub : unexpected exception"));
        }
    }

    // 허브 수정
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<HubUpdateResponserDto> updateHub(HubUpdateRequestDto requestDto, long userId) {
        try {
            Hub originHub = hubRepository.findById(requestDto.getHubId()).get();
            if(originHub == null) {
                throw new ResourceNotFoundException();
            }
            originHub = Hub.builder()
                .address(requestDto.getAddress())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();
            originHub.update(userId);
            Hub updateHub = hubRepository.save(originHub);
            return ResponseEntity.ok(new HubUpdateResponserDto(updateHub, "Hub successfully updated."));
        }
        catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest()
                .body(new HubUpdateResponserDto(null, "Failed to update hub : unexpected exception"));
        }
    }
}
