package com.sparta.hubservice.hub_route.application.dijkstra;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.util.List;

public interface PathFinder {

    List<Hub> getShortPath(Hub from, Hub to);
}
