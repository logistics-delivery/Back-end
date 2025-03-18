package com.sparta.hubservice.controller;

import com.sparta.hubservice.application.dto.HubCreateResponseDto;
import com.sparta.hubservice.application.dto.HubDeleteResponseDto;
import com.sparta.hubservice.application.dto.HubRequestDto;
import com.sparta.hubservice.application.dto.HubResponseDto;
import com.sparta.hubservice.application.dto.HubUpdateRequestDto;
import com.sparta.hubservice.application.dto.HubUpdateResponseDto;
import com.sparta.hubservice.application.service.HubService;
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
    public ResponseEntity<String> hubTest(){
        return ResponseEntity.ok("connect hub-service");
    }

    @GetMapping
    public ResponseEntity<Page<HubResponseDto>> getHubs(
        @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
        return hubService.getHubs(pageable);
    }

    @GetMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> getHubById(@PathVariable("hub_id") UUID hubId) {
        return hubService.getHub(hubId);
    }

    @PostMapping
    public ResponseEntity<HubCreateResponseDto> createHub(@RequestBody HubRequestDto requestDto, @RequestParam long userId) {
        return hubService.createHub(requestDto, userId);
    }

    @PutMapping
    public ResponseEntity<HubUpdateResponseDto> updateHub(@RequestBody HubUpdateRequestDto requestDto,  @RequestParam long userId) {
        return hubService.updateHub(requestDto, userId);
    }

    @DeleteMapping("{hub_id}")
    public ResponseEntity<HubDeleteResponseDto> deleteHub(@PathVariable("hub_id") UUID hubId, @RequestParam long userId) {
        return hubService.deleteHub(hubId, userId);
    }


}
