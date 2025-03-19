package com.sparta.hubservice.hub.presentation.controller;


import com.sparta.hubservice.hub.application.dto.response.HubRouteResponse;
import com.sparta.hubservice.hub.application.service.HubRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hub-routes")
@RequiredArgsConstructor
public class HubRoutesController {

    private final HubRoutesService hubRoutesService;

    // 허브 간 경로 조회
    @GetMapping("/{from_hub_id}/{to_hub_id}")
    public ResponseEntity<HubRouteResponse> getHubRoute(@PathVariable String from_hub_id, @PathVariable String to_hub_id) {
        HubRouteResponse response =  hubRoutesService.getHubRoute(from_hub_id, to_hub_id);
        return ResponseEntity.ok(response);
    }

}
