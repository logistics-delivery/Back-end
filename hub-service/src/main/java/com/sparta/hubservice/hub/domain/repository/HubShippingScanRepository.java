package com.sparta.hubservice.hub.domain.repository;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;

public interface HubShippingScanRepository {

    void save(HubShippingScanLog hubShippingScanLog);
}
