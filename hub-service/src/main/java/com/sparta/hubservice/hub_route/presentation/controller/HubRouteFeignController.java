package com.sparta.hubservice.hub_route.presentation.controller;

import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDetailsResponseDto;
import com.sparta.hubservice.hub_route.application.service.HubRouteService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub-route/feign")
public class HubRouteFeignController {

    private final HubRouteService hubRouteService;

    @GetMapping("/{from_id}/{to_id}/path")
    public ResponseEntity<HubRouteDetailsResponseDto> getHubRouteDetails(
        @PathVariable("from_id") UUID fromId,
        @PathVariable("to_id") UUID toId
    ){
        HubRouteDetailsResponseDto responseDto = hubRouteService.getPathHubRoute(fromId, toId);
        return ResponseEntity.ok(responseDto);
    }
}
