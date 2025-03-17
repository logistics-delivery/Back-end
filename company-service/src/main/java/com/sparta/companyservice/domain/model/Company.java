package com.sparta.companyservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Company extends BaseEntity {
    @Id
    @Column(name = "company_id")
    private UUID id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyType type;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    public static Company create(UUID id, String name, CompanyType type, UUID hubId, String address) {
        return Company.builder()
                .id(id)
                .name(name)
                .type(type)
                .hubId(hubId)
                .address(address)
                .build();
    }
}

