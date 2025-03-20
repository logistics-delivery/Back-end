package com.sparta.hubservice.hub.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.application.dto.response.HubRouteResponse;
import com.sparta.hubservice.hub.domain.model.HubRoute;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "HubRoutesService")
public class HubRoutesService {

    private final HubRepository hubRepository;

    public HubRouteResponse getHubRoute(String fromHubIdString, String toHubIdString) {
        UUID fromHubId = UUID.fromString(fromHubIdString);
        UUID toHubId = UUID.fromString(toHubIdString);

        Optional<HubRoute> hubRoute = hubRepository.findByFromHubIdAndToHubIdAndIsDeletedFalse(fromHubId, toHubId);

        if(hubRoute.isEmpty()){
            throw new ResourceNotFoundException("Hub route not found");
        }

        return new HubRouteResponse(hubRoute.get());
    }
}
