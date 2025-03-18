package com.sparta.hubservice.hub.presentation.controller;

import com.sparta.hubservice.hub.application.dto.HubCreateResponseDto;
import com.sparta.hubservice.hub.application.dto.HubDeleteResponseDto;
import com.sparta.hubservice.hub.application.dto.HubRequestDto;
import com.sparta.hubservice.hub.application.dto.HubResponseDto;
import com.sparta.hubservice.hub.application.dto.HubUpdateRequestDto;
import com.sparta.hubservice.hub.application.dto.HubUpdateResponseDto;
import com.sparta.hubservice.hub.application.service.HubService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @GetMapping
    public ResponseEntity<Page<HubResponseDto>> getHubs(
        @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
        Page<HubResponseDto> responseDtos = hubService.getHubs(pageable);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> getHubById(@PathVariable("hub_id") UUID hubId) {
        HubResponseDto responseDto = hubService.getHub(hubId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<HubCreateResponseDto> createHub(@RequestBody HubRequestDto requestDto, @RequestParam long userId) {
        HubCreateResponseDto responseDto = hubService.createHub(requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<HubUpdateResponseDto> updateHub(@RequestBody HubUpdateRequestDto requestDto,  @RequestParam long userId) {
        HubUpdateResponseDto responseDto = hubService.updateHub(requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("{hub_id}")
    public ResponseEntity<HubDeleteResponseDto> deleteHub(@PathVariable("hub_id") UUID hubId, @RequestParam long userId) {
        HubDeleteResponseDto responseDto = hubService.deleteHub(hubId, userId);
        return ResponseEntity.ok(responseDto);
    }


}
