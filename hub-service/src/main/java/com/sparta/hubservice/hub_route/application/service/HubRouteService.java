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
        Page<HubRoute> hubRoutes = hubRouteRepository.findAllByIsDeletedFalse(pageable);

        if(hubRoutes.isEmpty()) {
            throw new ResourceNotFoundException("Hub routes not found");
        }

        return hubRoutes.map(HubRouteResponse::new);
    }

    // 특정 경로 ID 조회
    public HubRouteResponse getHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId).orElseThrow(ResourceNotFoundException::new);
        return new HubRouteResponse(hubRoute);
    }

    //특정 출발 허브 → 도착 허브 경로 조회
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
        Optional<Hub> fromHub = hubRepository.findById(fromHubId);
        Optional<Hub> toHub = hubRepository.findById(toHubId);

        // 거리 계산
        double distance = HaversineCalculator.haversineDistance(
            fromHub.get().getLatitude(), fromHub.get().getLongitude(),
            toHub.get().getLatitude(), toHub.get().getLongitude());

        // 시간 계산
        double speed = 60;
        int duration = (int)Math.round(distance / speed);

        HubRoute saveHubRoute = HubRoute.builder()
            .fromHub(fromHub.get())
            .toHub(toHub.get())
            .duration(duration)
            .distance(BigDecimal.valueOf(distance))
            .userId(userId)
            .build();

        HubRoute hubRoute = hubRouteRepository.save(saveHubRoute);
        return new HubRouteCreateResponse(hubRoute);
    }

    // 허브 간 경로 정보 삭제 (soft deleted)
    @Transactional
    public HubRouteDeleteResponse deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = hubRouteRepository.findByHubRouteIdAndIsDeletedFalse(hubRouteId);
        if(hubRoute == null){
            throw new ResourceNotFoundException("Hub route not found");
        }
        // hubRouteId와 관련있는 hub_route_checkpoint 정보 삭제
        checkpointRepository.findAllByHubRouteId(hubRouteId)
                .forEach(checkpoint -> checkpoint.delete(userId));

        hubRoute.delete(userId);
        return new HubRouteDeleteResponse(hubRouteId, "Hub successfully deleted.");
    }

}
