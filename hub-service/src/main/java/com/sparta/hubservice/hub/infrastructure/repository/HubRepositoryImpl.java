package com.sparta.hubservice.hub.infrastructure.repository;

import com.sparta.hubservice.hub.application.service.HubService;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.repository.HubRepository;
import com.sparta.hubservice.hub.infrastructure.persistence.JPAHubRepository;
import com.sparta.hubservice.hub.infrastructure.persistence.QueryDSLHubRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final JPAHubRepository jpaHubRepository;
    private final QueryDSLHubRepository queryDSLHubRepository;

    @Override
    public Page<Hub> findByIsDeletedFalse(Pageable pageable) {
        return jpaHubRepository.findByIsDeletedFalse(pageable);
    }

    @Override
    public Optional<Hub> findById(UUID hubId) {
        return jpaHubRepository.findById(hubId);
    }

    @Override
    public Hub save(Hub hub) {
        return jpaHubRepository.save(hub);
    }

    @Override
    public List<Hub> findAll() {
        return jpaHubRepository.findAll();
    }

    @Override
    public Optional<Page<Hub>> searchByKeyword(String name, String address, Pageable pageable) {
        return queryDSLHubRepository.searchByKeyword(name, address, pageable);
    }
}
