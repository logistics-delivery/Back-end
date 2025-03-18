package com.sparta.hubservice.domain.repository;

import com.sparta.hubservice.application.dto.HubResponseDto;
import com.sparta.hubservice.domain.model.Hub;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID> {
    Page<HubResponseDto> findAllAndIsDeletedFalse(Pageable pageable);

    boolean existsByName(String name);
}
