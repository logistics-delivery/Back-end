package com.sparta.hubservice.hub_route.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponse;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponse;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import jakarta.persistence.Table;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "HubRoutesService ")
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    // 허브 간 경로 조회
    @Transactional(readOnly = true)
    public HubRouteResponse getHubRoute(UUID fromHubId, UUID toHubId) {

        Optional<HubRoute> hubRoute = hubRouteRepository.findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(fromHubId, toHubId);

        if(hubRoute.isEmpty()){
            throw new ResourceNotFoundException("Hub route not found");
        }

        return new HubRouteResponse(hubRoute.get());
    }

    // 허브 간 경로 생성
    @Transactional
    public HubRouteCreateResponse createHubRoute(UUID fromHubId, UUID toHubId) {
        Optional<Hub> fromHub = hubRepository.findById(fromHubId);
        Optional<Hub> toHub = hubRepository.findById(toHubId);
        // fromHub.getAddress() 와 toHub.getAddress()를 가지고 거리, 시간 가져오기

        HubRoute saveHubRoute =
            HubRoute.builder()
            .fromHub(fromHub.get())
            .toHub(toHub.get())
            .build();

        return null;
    }

    // 허브 간 경로 정보 삭제 (soft deleted)
    @Transactional
    public HubRouteDeleteResponse deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = hubRouteRepository.findByHubRouteIdAndIsDeletedFalse(hubRouteId);
        if(hubRoute == null){
            throw new ResourceNotFoundException("Hub route not found");
        }

        hubRoute.delete(userId);
        return new HubRouteDeleteResponse(hubRouteId, "Hub successfully deleted.");
    }
}
