package com.sparta.hubservice.hub_route.application.dijkstra;

import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.model.HubRoute;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

public class JGraphTPathFinder implements PathFinder {

    private final Graph<Hub, HubRoute> graph; // Graph<V, E>

    public JGraphTPathFinder(Map<Hub, List<HubRoute>> graphMap) {

        Graph<Hub, HubRoute> graph = new DefaultDirectedWeightedGraph<>(HubRoute.class);

        for(Hub fromHub : graphMap.keySet()) {
            // from 꼭짓점 추가
            graph.addVertex(fromHub);
            List<HubRoute> routes = graphMap.get(fromHub);

            for(HubRoute route : routes) {
                // to 꼭짓점 추가
                graph.addVertex(route.getToHub());
                // graph(fromV, toV, e)
                graph.addEdge(fromHub, route.getToHub(),  route);
                // e 에 대한 가중치 설정
                graph.setEdgeWeight(route, route.getDistance().doubleValue());
            }
        }

        this.graph = graph;
    }

    // 경로 시퀀스
    @Override
    public List<Hub> getShortPath(Hub start, Hub end){
        GraphPath<Hub, HubRoute> path = new DijkstraShortestPath<>(graph).getPath(start, end);

        if (path == null) {
            throw new IllegalArgumentException(String.format(
                "허브 간 최단 경로가 존재하지 않습니다 : %s → %s", start.getName(), end.getName()
            ));
        }

        return path.getVertexList();
    }

}
