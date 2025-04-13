package com.sparta.shippingservice.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_shipping_route")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShippingRouteLog extends BaseEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID hubRouteId;

    @Column(nullable = false)
    private UUID fromHubId;

    @Column(nullable = false)
    private UUID toHubId;

    @Column(nullable = false)
    private int duration; // 총 소요 시간 (분)

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal distance; // 총 거리 (km 단위)
    // 체크포인트들 - OneToMany

    @OneToMany(mappedBy = "shippingRouteLog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShippingCheckpoint> checkpoints = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "shipping_shipping_id")
    private Shipping shipping;

    // 연관관계 편의 메서드
    public void addCheckpoint(ShippingCheckpoint checkpoint) {
        checkpoints.add(checkpoint);
        checkpoint.setShippingRoute(this);

    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }


    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }
}