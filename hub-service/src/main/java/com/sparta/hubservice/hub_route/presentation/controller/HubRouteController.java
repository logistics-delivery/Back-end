package com.sparta.hubservice.hub_route.presentation.controller;


import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponse;
import com.sparta.hubservice.hub_route.application.service.HubRouteService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

    private final HubRouteService hubRoutesService;

    // 허브 간 경로 조회 -> 기능 추가 예정
    @GetMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteResponse> getHubRoute(@PathVariable String from_hub_id, @PathVariable String to_hub_id) {
        HubRouteResponse response =  hubRoutesService.getHubRoute(UUID.fromString(from_hub_id), UUID.fromString(to_hub_id));
        return ResponseEntity.ok(response);
    }

    // 허브 간 경로 생성
    // 미완성-> naver api 이용하여 자동 경로 생성으로 기능 추가 예정
    @PostMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteCreateResponse> createHubRoute(@PathVariable String from_hub_id, @PathVariable String to_hub_id) {
        HubRouteCreateResponse response = hubRoutesService.createHubRoute(UUID.fromString(from_hub_id), UUID.fromString(to_hub_id));
        return ResponseEntity.ok(response);
    }

    // 허브 간 경로 정보 삭제
    @DeleteMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteDeleteResponse> deleteHubRoute(@PathVariable String hub_route_id, @RequestParam Long userId) {
        HubRouteDeleteResponse response = hubRoutesService.deleteHubRoute(UUID.fromString(hub_route_id), userId);
        return ResponseEntity.ok(response);
    }


}
