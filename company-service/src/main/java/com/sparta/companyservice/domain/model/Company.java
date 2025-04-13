package com.sparta.companyservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_company")
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
    private Company(UUID id, String name, CompanyType type, UUID hubId, String address, long userId) {
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

    public void update(String newName, String newAddress, UUID newHubId, long userId) {
        validateCompany(newName, newAddress, newHubId);
        this.name = newName;
        this.address = newAddress;
        this.hubId = newHubId;
        super.update(userId);
    }

    public void applyUpdate(CompanyUpdateDto dto, long userId) {
        // 수정 시 사용자가 입력하지 않은 필드는 기존 값으로 씌움
        String newName = dto.name() != null ? dto.name() : this.name;
        String newAddress = dto.address() != null ? dto.address() : this.address;
        UUID newHubId = dto.hubId() != null ? dto.hubId() : this.hubId;

        update(newName, newAddress, newHubId, userId);
    }

    public void delete(long userId) {
        super.delete(userId);
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////

    // 업체 수정 시 검증
    private static void validateCompany(String newName, String newAddress, UUID newHubId) {
        validateNotNull(newName, "업체명");
        validateNotNull(newAddress, "주소");
        validateNotNull(newHubId, "소속 Hub");
    }

    // 업체 생성 시 검증
    private static void validateCompany(String newName, String newAddress, UUID newHubId, CompanyType newType) {
        validateCompany(newName, newAddress, newHubId);
        validateNotNull(newType, "업체 type");
    }

    private static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "은(는) null일 수 없습니다.");
        }
    }
}

