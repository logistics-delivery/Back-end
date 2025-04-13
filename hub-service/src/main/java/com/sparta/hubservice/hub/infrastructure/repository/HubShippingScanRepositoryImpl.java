package com.sparta.hubservice.hub.infrastructure.repository;

import com.sparta.hubservice.hub.domain.model.HubShippingScanLog;
import com.sparta.hubservice.hub.domain.repository.HubShippingScanRepository;
import com.sparta.hubservice.hub.infrastructure.persistence.JPAHubShippingScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubShippingScanRepositoryImpl implements HubShippingScanRepository {

    private final JPAHubShippingScanRepository jpaHubShippingScanRepository;

    @Override
    public void save(HubShippingScanLog hubShippingScanLog) {
        jpaHubShippingScanRepository.save(hubShippingScanLog);
    }


}
