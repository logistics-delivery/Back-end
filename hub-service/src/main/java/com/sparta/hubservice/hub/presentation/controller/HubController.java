package com.sparta.hubservice.hub.presentation.controller;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.hubservice.hub.application.dto.request.HubRequestDto;
import com.sparta.hubservice.hub.application.dto.response.HubCreateResponseDto;
import com.sparta.hubservice.hub.application.dto.response.HubDeleteResponseDto;
import com.sparta.hubservice.hub.application.dto.response.HubResponseDto;
import com.sparta.hubservice.hub.application.dto.response.HubUpdateResponseDto;
import com.sparta.hubservice.hub.application.service.HubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
@Tag(name = "Hub Service", description = "허브 서비스 API")
public class HubController {

    private final HubService hubService;

    // 허브 전체 조회
    @Operation(summary = "Hub 전체 조회", description = "허브 전체 조회 api")
    @GetMapping
    public ResponseEntity<Page<HubResponseDto>> getHubs(
        @PageableDefault(page = 0, size = 30, sort = "createdAt") Pageable pageable) {
        Page<HubResponseDto> responseDtos = hubService.getHubs(pageable);
        return ResponseEntity.ok(responseDtos);
    }

    // 특정 허브 조회
    @Operation(summary = "Hub 단일 조회", description = "허브 단일 조회 api")
    @GetMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> getHubById(@PathVariable("hub_id") UUID hubId) {
        HubResponseDto responseDto = hubService.getHub(hubId);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 검색
    @Operation(summary = "Hub 검색", description = "허브 검색 api")
    @GetMapping("/search")
    public ResponseEntity<Page<HubResponseDto>> getSearchHubs(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String address,
        @PageableDefault(page = 0, size = 30, sort = "createdAt") Pageable pageable){
        Page<HubResponseDto> responseDto = hubService.getSearchHubs(name, address, pageable);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 생성
    @Operation(summary = "Hub 생성", description = "허브 생성 api")
    @RoleCheck("ROLE_MASTER")
    @PostMapping
    public ResponseEntity<HubCreateResponseDto> createHub(@RequestBody @Valid HubRequestDto requestDto,@RequestHeader("user-id") Long userId) {
        HubCreateResponseDto responseDto = hubService.createHub(requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 수정
    @Operation(summary = "Hub 수정", description = "허브 수정 api")
    @RoleCheck("ROLE_MASTER")
    @PutMapping("{hub_id}")
    public ResponseEntity<HubUpdateResponseDto> updateHub(@PathVariable("hub_id") UUID hubId, @RequestParam String address,  @RequestHeader("user-id") Long userId) {
        HubUpdateResponseDto responseDto = hubService.updateHub(hubId, address, userId);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 삭제
    @Operation(summary = "Hub 삭제", description = "허브 삭제 api")
    @RoleCheck("ROLE_MASTER")
    @DeleteMapping("{hub_id}")
    public ResponseEntity<HubDeleteResponseDto> deleteHub(@PathVariable("hub_id") UUID hubId, @RequestHeader("user-id") Long userId) {
        HubDeleteResponseDto responseDto = hubService.deleteHub(hubId, userId);
        return ResponseEntity.ok(responseDto);
    }



}
