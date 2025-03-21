package com.sparta.hubservice.hub_route.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponse;
import com.sparta.hubservice.hub_route.domain.common.HaversineCalculator;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteCheckpointRepository;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "HubRoutesService ")
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;
    private final HubRouteCheckpointRepository checkpointRepository;

    // 전체 허브 간 경로 목록 조회
    @Transactional(readOnly = true)
    public Page<HubRouteResponse> getHubRoutes(Pageable pageable) {
        Page<HubRoute> hubRoutes = hubRouteRepository.findAllByIsDeletedFalse(pageable)
            .orElseThrow(ResourceNotFoundException::new);

        return hubRoutes.map(HubRouteResponse::new);
    }

    // 특정 경로 ID 조회
    public HubRouteResponse getHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId).orElseThrow(ResourceNotFoundException::new);
        return new HubRouteResponse(hubRoute);
    }

    // 출발 허브 -> 도착 허브 : 특정 경로 조회
    @Transactional(readOnly = true)
    public HubRouteResponse getHubRouteFromHubToHub(UUID fromHubId, UUID toHubId) {

        Optional<HubRoute> hubRoute = hubRouteRepository.findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(fromHubId, toHubId);

        if(hubRoute.isEmpty()){
            throw new ResourceNotFoundException("Hub route not found");
        }
        return new HubRouteResponse(hubRoute.get());
    }

    // 허브 간 경로 생성
    @Transactional
    public HubRouteCreateResponse createHubRoute(Long userId, UUID fromHubId, UUID toHubId) {
        Hub fromHub = hubRepository.findById(fromHubId).get();
        Hub toHub = hubRepository.findById(toHubId).get();

        HubRoute saveHubRoute = HubRoute.builder()
            .fromHub(fromHub)
            .toHub(toHub)
            .userId(userId)
            .build();

        HubRoute hubRoute = hubRouteRepository.save(saveHubRoute)
            .orElseThrow(ResourceNotFoundException::new);
        return new HubRouteCreateResponse(hubRoute);
    }

    // 허브 간 경로 정보 삭제 (soft deleted)
    @Transactional
    public HubRouteDeleteResponse deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = hubRouteRepository.findByHubRouteIdAndIsDeletedFalse(hubRouteId)
            .orElseThrow(ResourceNotFoundException::new);

        // hubRouteId와 관련있는 hub_route_checkpoint 정보 삭제
        checkpointRepository.findAllByHubRouteId(hubRouteId)
                .forEach(checkpoint -> checkpoint.delete(userId));

        hubRoute.delete(userId);
        return new HubRouteDeleteResponse(hubRouteId, "Hub successfully deleted.");
    }

}
