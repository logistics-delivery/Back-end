package com.sparta.shippingservice.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_shipping_route_logs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"shipping_id", "sequence"})) // 동일 배송에 같은 순번 불가능
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingRouteLog extends BaseEntity {

    @Id
    @Column(name = "shipping_route_log_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_id", nullable = false)
    private Shipping shipping;

    @Column(name = "start_hub_id", nullable = false)
    private UUID startHubId;

    @Column(name = "end_hub_id", nullable = false)
    private UUID endHubId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "estimated_distance", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedDistance;

    @Column(name = "estimated_time", nullable = false)
    private Integer estimatedTime;

    @Column(name = "actual_distance")
    private BigDecimal actualDistance;

    @Column(name = "actual_time")
    private Integer actualTime;

    @Column(name = "shipping_manager_id", nullable = false)
    private UUID shippingManagerId;

    public ShippingRouteLog(UUID startHubId, UUID endHubId, Integer sequence, BigDecimal estimatedDistance, Integer estimatedTime, BigDecimal actualDistance, Integer actualTime, UUID shippingManagerId) {

        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.sequence = sequence;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.shippingManagerId = shippingManagerId;
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }
}
