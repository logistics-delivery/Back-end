package com.sparta.hubservice.hub.domain.repository;

import com.sparta.hubservice.hub.domain.model.Hub;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubQueryRepository {
    Optional<Page<Hub>> searchByKeyword(String name, String address, Pageable pageable);
}
