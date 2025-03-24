package com.sparta.hubservice.hub_route.application.dijkstra;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub_route.application.dto.serviceDto.PathValueDto;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import com.sparta.hubservice.hub_route.domain.repository.HubRouteRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PathCalculate {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    public List<Hub> getShortPath(Hub fromHub, Hub toHub) {

        // 그래프 정의
        Map<Hub, List<HubRoute>> graph = new HashMap<>();
        List<Hub> hubs = hubRepository.findAll();
        for (Hub hub : hubs) {
            List<HubRoute> routes = hubRouteRepository.findByFromHub(hub)
                .orElse(Collections.emptyList());

            graph.putIfAbsent(hub, new ArrayList<>());
            graph.get(hub).addAll(routes);

            for (HubRoute route : routes) {
                Hub to = route.getToHub();
                graph.putIfAbsent(to, new ArrayList<>());

                HubRoute reversed = hubRouteRepository.findShortestRouteByFromAndTo(to, route.getFromHub()).orElse(null);
                graph.get(to).add(reversed);
            }
        }

        // 직접 정의한 dijkstra를 이용한 체크포인트 리스트 생성
        DijkstraPathFinder dijkstraPathFinder = new DijkstraPathFinder(graph);
        List<Hub> sequencePathByDijkstra = dijkstraPathFinder.getShortPath(fromHub, toHub);

        // JGraphT 라이브러리를 사용한 체크포인트 리스트 생성
        JGraphTPathFinder jgraphT = new JGraphTPathFinder(graph);
        List<Hub> sequencePathByJGraphT = jgraphT.getShortPath(fromHub, toHub);

        if(sequencePathByDijkstra.equals(sequencePathByJGraphT)) {
            log.info("Checking checkpoint path validity : dijkstraPath same JGraphT");
        }

        return sequencePathByDijkstra;
    }

    public PathValueDto getValue(List<Hub> shortPath){

        BigDecimal totalDistance = BigDecimal.ZERO;
        int totalDuration = 0;

        for(int i = 0; i < shortPath.size()-1; i++){
            Hub h1 = shortPath.get(i);
            Hub h2 = shortPath.get(i+1);
            HubRoute route = hubRouteRepository.findShortestRouteByFromAndTo(h1, h2)
                .orElseThrow(ResourceNotFoundException::new);
            totalDistance = totalDistance.add(route.getDistance());
            totalDuration += route.getDuration();
        }

        return new PathValueDto(totalDistance, totalDuration);
    }


}
