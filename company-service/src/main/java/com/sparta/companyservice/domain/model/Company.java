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
    // 도메인 객체 생성 책임은 create()가 지고, 그 내부에서 builder를 통해 객체 생성
    public Company(UUID id, String name, CompanyType type, UUID hubId, String address, long userId) {
        super(userId);
        this.id = id;
        this.name = name;
        this.type = type;
        this.hubId = hubId;
        this.address = address;
    }

    public static Company create(String name, String address, UUID hubId, CompanyType type, long userId) {
        validateCompany(name, address, hubId, type);
        return Company.builder()
                .id(UUID.randomUUID())
                .name(name)
                .address(address)
                .hubId(hubId)
                .type(type)
                .userId(userId)
                .build();
    }

    public void update(String newName, String newAddress, UUID newHubId, CompanyType newType, long userId) {
        validateCompany(newName, newAddress, newHubId, newType);
        this.name = newName;
        this.address = newAddress;
        this.hubId = newHubId;
        this.type = newType;
        super.update(userId);
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////

    // 업체 생성, 수정 시 검증

    private static void validateCompany(String newName, String newAddress, UUID newHubId, CompanyType newType) {
        validateCompanyNewName(newName);
        validateCompanyNewAddress(newAddress);
        validateCompanyNewHubId(newHubId);
        validateCompanyNewType(newType);
    }

    private static void validateCompanyNewType(CompanyType newType) {
        if(newType == null) {
            throw new IllegalArgumentException("업체 type은 null일 수 없습니다.");
        }
    }

    private static void validateCompanyNewHubId(UUID newHubId) {
        if (newHubId == null) {
            throw new IllegalArgumentException("소속 Hub는 null일 수 없습니다.");
        }
    }

    private static void validateCompanyNewAddress(String newAddress) {
        if (newAddress == null) {
            throw new IllegalArgumentException("주소는 null일 수 없습니다.");
        }
    }

    private static void validateCompanyNewName(String newName) {
        if (newName == null) {
            throw new IllegalArgumentException("업체명은 null일 수 없습니다.");
        }
    }
}

