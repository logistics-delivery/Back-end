package com.sparta.hubservice.hub_route.presentation.controller;


import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDetailsResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponseDto;
import com.sparta.hubservice.hub_route.application.service.HubRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs/hub-routes")
@RequiredArgsConstructor
@Tag(name = "Hub Service", description = "허브 서비스 API")
public class HubRouteController {

    private final HubRouteService hubRouteService;

    // 전체 허브 간 경로 목록 조회 (direct)
    @Operation(summary = "Hub Route", description = "전체 허브 간 경로 조회 api")
    @GetMapping
    public ResponseEntity<Page<HubRouteResponseDto>> getHubRoutes(
        @PageableDefault(page = 0, size = 30, sort = "createdAt") Pageable pageable){
        Page<HubRouteResponseDto> responses = hubRouteService.getHubRoutes(pageable);
        return ResponseEntity.ok(responses);
    }

    // 특정 경로 ID 조회
    @Operation(summary = "Hub Route", description = "id기반 허브 간 경로 조회 api")
    @GetMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteResponseDto> getHubRoute(@PathVariable("hub_route_id") UUID hubRouteId) {
        HubRouteResponseDto response = hubRouteService.getHubRoute(hubRouteId);
        return ResponseEntity.ok(response);
    }

    // 특정 출발 허브 → 도착 허브 경로 조회 (direct)
    @Operation(summary = "Hub Route", description = "from->to 허브 간 경로 조회 api")
    @GetMapping("/{from_hub_id}/{to_hub_id}/direct")
    public ResponseEntity<HubRouteResponseDto> getDirectHubRoute(
        @PathVariable("from_hub_id") UUID fromHubId,
        @PathVariable("to_hub_id") UUID toHubId) {
        HubRouteResponseDto response =  hubRouteService.getDirectHubRoute(fromHubId, toHubId);
        return ResponseEntity.ok(response);
    }

    // (정해진) 허브 간 경로 생성 (direct)
    @Operation(summary = "Hub Route", description = "from->to 허브 간 경로 생성 api")
    @PostMapping("/{from_hub_id}/{to_hub_id}/direct")
    public ResponseEntity<HubRouteCreateResponseDto> createDirectHubRoute(
        @PathVariable("from_hub_id") UUID fromHubId,
        @PathVariable("to_hub_id") UUID toHubId,
        @RequestHeader("user_id") Long userId) {
        HubRouteCreateResponseDto response =
            hubRouteService.createDirectHubRoute(userId, fromHubId, toHubId);
        return ResponseEntity.ok(response);
    }

    // 허브 간 경로 정보 삭제
    @Operation(summary = "Hub Route", description = "허브 간 경로 삭제 api")
    @RoleCheck("ROLE_MASTER")
    @DeleteMapping("/{hub_route_id}")
    public ResponseEntity<HubRouteDeleteResponseDto> deleteHubRoute(
        @PathVariable("hub_route_id") UUID hubRouteId,
        @RequestHeader("user_id") Long userId) {
        HubRouteDeleteResponseDto response = hubRouteService.deleteHubRoute(hubRouteId, userId);
        return ResponseEntity.ok(response);
    }

    // form -> to 최단경로 생성 (다이렉트는 항상 최단경로)
    @Operation(summary = "Hub Route - Checkpoint", description = "허브 간 최단 경로 생성 api")
    @PostMapping("/{from_hub_id}/{to_hub_id}/path")
    public ResponseEntity<HubRouteDetailsResponseDto> createPathHubRoute(
        @PathVariable("from_hub_id") UUID fromHubId,
        @PathVariable("to_hub_id") UUID toHubId,
        @RequestHeader("user_id") Long userId
    ){
        HubRouteDetailsResponseDto response =
            hubRouteService.createPathHubRoute(fromHubId, toHubId, userId);
        return ResponseEntity.ok(response);
    }

    // fromHub -> toHub 최단 경로 정보 조회
    @Operation(summary = "Hub Route - Checkpoint", description = "허브 간 최단 경로 조회 api")
    @GetMapping("/{from_hub_id}/{to_hub_id}/path")
    public ResponseEntity<HubRouteDetailsResponseDto> getPathHubRoute(
        @PathVariable("from_hub_id") UUID fromHubId,
        @PathVariable("to_hub_id") UUID toHubId
    ){
        HubRouteDetailsResponseDto responseDto =
            hubRouteService.getPathHubRoute(fromHubId, toHubId);
        return ResponseEntity.ok(responseDto);
    }


}
