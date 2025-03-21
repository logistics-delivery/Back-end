package com.sparta.hubservice.hub_route.presentation.controller;


import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponse;
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
    public ResponseEntity<Page<HubRouteResponse>> getHubRoutes(
        @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable){
        Page<HubRouteResponse> responses = hubRouteService.getHubRoutes(pageable);
        return ResponseEntity.ok(responses);
    }

    // 특정 경로 ID 조회
    @GetMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteResponse> getHubRoute(@PathVariable("hub_route_id") UUID hubRouteId) {
        HubRouteResponse response = hubRouteService.getHubRoute(hubRouteId);
        return ResponseEntity.ok(response);
    }

    // 특정 출발 허브 → 도착 허브 경로 조회
    @GetMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteResponse> getHubRoute(
        @PathVariable("from_hub_id") String fromHubId,
        @PathVariable("to_hub_id") String toHubId) {
        HubRouteResponse response =  hubRouteService.getHubRouteFromHubToHub(UUID.fromString(fromHubId), UUID.fromString(toHubId));
        return ResponseEntity.ok(response);
    }

    // (정해진) 허브 간 경로 생성
    // Todo : 더미데이터 필요 (추후 중요한 경로만 주기적으로 naver api 실시간 거리 및 시간 반영 고려)
    @PostMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteCreateResponse> createHubRoute(
        @PathVariable("from_hub_id") String fromHubId,
        @PathVariable("to_hub_id") String toHubId,
        @RequestParam Long userId) {
        HubRouteCreateResponse response = hubRouteService.createHubRoute(userId, UUID.fromString(fromHubId), UUID.fromString(toHubId));
        return ResponseEntity.ok(response);
    }

    // 허브 간 경로 정보 삭제
    @DeleteMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteDeleteResponse> deleteHubRoute(
        @PathVariable("hub_route_id") String hubRouteId,
        @RequestParam Long userId) {
        HubRouteDeleteResponse response = hubRouteService.deleteHubRoute(UUID.fromString(hubRouteId), userId);
        return ResponseEntity.ok(response);
    }


}
