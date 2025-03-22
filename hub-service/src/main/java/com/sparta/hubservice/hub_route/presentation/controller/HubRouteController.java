package com.sparta.hubservice.hub_route.presentation.controller;


import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDetailsResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponseDto;
import com.sparta.hubservice.hub_route.application.service.HubRouteService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    // 전체 허브 간 경로 목록 조회
    @GetMapping
    public ResponseEntity<Page<HubRouteResponseDto>> getHubRoutes(
        @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable){
        Page<HubRouteResponseDto> responses = hubRouteService.getHubRoutes(pageable);
        return ResponseEntity.ok(responses);
    }

    // 특정 경로 ID 조회
    @GetMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteResponseDto> getHubRoute(@PathVariable("hub_route_id") UUID hubRouteId) {
        HubRouteResponseDto response = hubRouteService.getHubRoute(hubRouteId);
        return ResponseEntity.ok(response);
    }

    // 특정 출발 허브 → 도착 허브 경로 조회 (direct)
    @GetMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteResponseDto> getDirectHubRoute(
        @PathVariable("from_hub_id") String fromHubId,
        @PathVariable("to_hub_id") String toHubId) {
        HubRouteResponseDto response =  hubRouteService.getDirectHubRoute(UUID.fromString(fromHubId), UUID.fromString(toHubId));
        return ResponseEntity.ok(response);
    }

    // (정해진) 허브 간 경로 생성 (direct)
    // Todo : 더미데이터 필요
    @PostMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteCreateResponseDto> createDirectHubRoute(
        @PathVariable("from_hub_id") String fromHubId,
        @PathVariable("to_hub_id") String toHubId,
        @RequestParam Long userId) {
        HubRouteCreateResponseDto response = hubRouteService.createDirectHubRoute(userId, UUID.fromString(fromHubId), UUID.fromString(toHubId));
        return ResponseEntity.ok(response);
    }

    // 허브 간 경로 정보 삭제
    @DeleteMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteDeleteResponseDto> deleteHubRoute(
        @PathVariable("hub_route_id") String hubRouteId,
        @RequestParam Long userId) {
        HubRouteDeleteResponseDto response = hubRouteService.deleteHubRoute(UUID.fromString(hubRouteId), userId);
        return ResponseEntity.ok(response);
    }

    // form -> to 최단경로 생성
    @PostMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteDetailsResponseDto> createPathHubRoute(
        @PathVariable("from_hub_id") String fromHubId,
        @PathVariable("to_hub_id") String toHubId,
        @RequestParam Long userId
    ){
        HubRouteDetailsResponseDto response = hubRouteService.createPathHubRoute(UUID.fromString(fromHubId), UUID.fromString(toHubId), userId);
        return ResponseEntity.ok(response);
    }

    // fromHub -> toHub 최단 경로 시퀀스 조회


}
