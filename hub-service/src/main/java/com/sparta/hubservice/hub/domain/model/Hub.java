package com.sparta.hubservice.hub.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub")
public class Hub extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(unique = true, nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 8)
    private BigDecimal latitude;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 8)
    private BigDecimal longitude;

    @Builder
    public Hub(String name, String address, BigDecimal latitude, BigDecimal longitude, long userId) {
        super(userId);
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateHub(String address, BigDecimal latitude, BigDecimal longitude, long userId){
        super.update(userId);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
