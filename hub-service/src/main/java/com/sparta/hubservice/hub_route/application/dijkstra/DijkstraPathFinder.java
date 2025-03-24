package com.sparta.hubservice.hub_route.application.dijkstra;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.Getter;

@Getter
class HubNode implements Comparable<HubNode> {
    private final Hub hub;
    private final BigDecimal distance;
    private final Hub beforeHub;

    public HubNode(Hub hub, BigDecimal distance,  Hub beforeHub) {
        this.hub = hub;
        this.distance = distance;
        this.beforeHub = beforeHub;
    }

    @Override
    public int compareTo(HubNode other) {
        return  this.distance.compareTo(other.distance);
    }
}

public class DijkstraPathFinder implements PathFinder {

    private final Map<Hub, List<HubRoute>> graph;

    public DijkstraPathFinder(Map<Hub, List<HubRoute>>  graph) {
        this.graph = graph;
    }

    @Override
    public List<Hub> getShortPath(Hub start, Hub end) {

        Map<Hub, BigDecimal> distances = new HashMap<>();
        Map<Hub, Hub> beforeHub = new HashMap<>();
        Set<Hub> visited = new HashSet<>();
        PriorityQueue<HubNode> queue = new PriorityQueue<>();

        distances.put(start, BigDecimal.ZERO);
        queue.offer(new HubNode(start, BigDecimal.ZERO, null));

        while (!queue.isEmpty()) {
            HubNode currentNode = queue.poll();
            Hub currentHub = currentNode.getHub();

            if (currentHub.equals(end)) break;

            if (visited.contains(currentHub)) continue;
            visited.add(currentHub);

            // 현재 허브에서 연결된 모든 허브 경로 가져오기
            List<HubRoute> routes = graph.getOrDefault(currentHub, List.of());


            for (HubRoute route : routes) {
                Hub nextHub = route.getToHub();
                BigDecimal newDistance = distances.get(currentHub).add(route.getDistance());

                // nextHub의 거리정보가 없거나, 기존 경로보다 짧으면 값 변경
                if(!distances.containsKey(nextHub) ||
                    newDistance.compareTo(distances.get(nextHub)) < 0){

                    distances.put(nextHub, newDistance);
                    beforeHub.put(nextHub, currentHub);
                    queue.offer(new HubNode(nextHub, newDistance, currentHub));

                }
            }
        }
        // 최단 경로 시퀀스
        List<Hub> sequence = new LinkedList<>();
        Hub current = end;
        while (current != null) {
            sequence.add(0, current);
            current = beforeHub.get(current);
        }

        return sequence;
    }

}
