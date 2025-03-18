package com.sparta.companyservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Company(UUID id, String name, CompanyType type, UUID hubId, String address, long userId) {
        super(userId);
        this.id = id;
        this.name = name;
        this.type = type;
        this.hubId = hubId;
        this.address = address;
    }
}

