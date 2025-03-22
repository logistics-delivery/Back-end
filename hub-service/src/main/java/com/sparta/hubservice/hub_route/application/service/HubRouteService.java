package com.sparta.hubservice.hub_route.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub_route.application.dijkstra.PathCalculate;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteCreateResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDeleteResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteDetailsResponseDto;
import com.sparta.hubservice.hub_route.application.dto.response.HubRouteResponseDto;
import com.sparta.hubservice.hub_route.application.dto.serviceDto.PathValueDto;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.model.HubRouteCheckpoint;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteCheckpointRepository;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import jakarta.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
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
    private final PathCalculate pathCalculate;

    // 전체 허브 간 경로 목록 조회
    @Transactional(readOnly = true)
    public Page<HubRouteResponseDto> getHubRoutes(Pageable pageable) {
        Page<HubRoute> hubRoutes = hubRouteRepository.findAllByIsDeletedFalse(pageable)
            .orElseThrow(ResourceNotFoundException::new);

        return hubRoutes.map(HubRouteResponseDto::new);
    }

    // 특정 경로 ID 조회
    public HubRouteResponseDto getHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId).orElseThrow(ResourceNotFoundException::new);
        return new HubRouteResponseDto(hubRoute);
    }

    // 출발 허브 -> 도착 허브 : 특정 경로 조회 (direct)
    @Transactional(readOnly = true)
    public HubRouteResponseDto getDirectHubRoute(UUID fromHubId, UUID toHubId) {

        Optional<HubRoute> hubRoute = hubRouteRepository.findByFromHub_HubIdAndToHub_HubIdAndIsDeletedFalse(fromHubId, toHubId);

        if(hubRoute.isEmpty()){
            throw new ResourceNotFoundException("Hub route not found");
        }
        return new HubRouteResponseDto(hubRoute.get());
    }

    // 허브 간 경로 생성 (direct 연결)
    @Transactional
    public HubRouteCreateResponseDto createDirectHubRoute(Long userId, UUID fromHubId, UUID toHubId) {
        Hub fromHub = hubRepository.findById(fromHubId).orElseThrow(ResourceNotFoundException::new);
        Hub toHub = hubRepository.findById(toHubId).orElseThrow(ResourceNotFoundException::new);

        HubRoute saveHubRoute = new HubRoute(fromHub, toHub, userId);

        HubRoute hubRoute = hubRouteRepository.save(saveHubRoute);
        return new HubRouteCreateResponseDto(hubRoute);
    }

    // 허브 간 경로 정보 삭제 (soft deleted)
    @Transactional
    public HubRouteDeleteResponseDto deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = hubRouteRepository.findByHubRouteIdAndIsDeletedFalse(hubRouteId)
            .orElseThrow(ResourceNotFoundException::new);

        // hubRouteId와 관련있는 hub_route_checkpoint 정보 삭제
        checkpointRepository.findAllByHubRouteId(hubRouteId)
                .forEach(checkpoint -> checkpoint.delete(userId));

        hubRoute.delete(userId);
        return new HubRouteDeleteResponseDto(hubRouteId, "Hub successfully deleted.");
    }

    // fromHub -> toHub 최단 경로 생성
    @Transactional
    public HubRouteDetailsResponseDto createPathHubRoute(UUID fromHubId, UUID toHubId, Long userId) {
        Hub fromHub = hubRepository.findById(fromHubId).orElseThrow(ResourceNotFoundException::new);
        Hub toHub = hubRepository.findById(toHubId).orElseThrow(ResourceNotFoundException::new);

        List<Hub> shortPath = pathCalculate.getShortPath(fromHub, toHub);
        PathValueDto vlaues = pathCalculate.getValue(shortPath);

        // 다이렉트는 이미 최단 경로 -> 이미 route 정보가 있다면 패스
        if(hubRouteRepository.findByFromHubAndToHub(fromHub, toHub).isPresent()){
            throw new EntityExistsException("Hub route already exists");
        }

        HubRoute route = new HubRoute(fromHub, toHub, vlaues.getTotalDuration(), vlaues.getTotalDistance(), userId);
        route = hubRouteRepository.save(route);

        List<HubRouteCheckpoint> checkpointList = new ArrayList<>();
        for(int i = 0; i < shortPath.size(); i++) {
            HubRouteCheckpoint result = HubRouteCheckpoint.builder()
                .checkpointHub(shortPath.get(i))
                .hubRoute(route)
                .userId(userId)
                .sequence(i)
                .build();
            checkpointList.add(result);
        }
        checkpointRepository.saveAll(checkpointList);

        return new HubRouteDetailsResponseDto(route, checkpointList);
    }

    // fromHub -> toHub 최단 경로 정보 조회
    @Transactional(readOnly = true)
    public HubRouteDetailsResponseDto getPathHubRoute(UUID fromHubId, UUID toHubId) {
        Hub fromHub = hubRepository.findById(fromHubId).orElseThrow(ResourceNotFoundException::new);
        Hub toHub = hubRepository.findById(toHubId).orElseThrow(ResourceNotFoundException::new);

        HubRoute route = hubRouteRepository.findByFromHubAndToHub(fromHub, toHub)
            .orElseThrow(ResourceNotFoundException::new);

        List <HubRouteCheckpoint> checkpointList =
            checkpointRepository.findAllByHubRouteIdOrderBySequenceAsc(route.getHubRouteId());

        return new HubRouteDetailsResponseDto(route, checkpointList);
    }
    


}
