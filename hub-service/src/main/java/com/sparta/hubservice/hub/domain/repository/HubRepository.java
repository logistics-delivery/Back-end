package com.sparta.hubservice.hub.domain.repository;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepository {
    Page<Hub> findByIsDeletedFalse(Pageable pageable);
    boolean existsByName(String name);

    Optional<Hub> findById(UUID hubId);

    <S extends Hub> S save(S hub);

}
