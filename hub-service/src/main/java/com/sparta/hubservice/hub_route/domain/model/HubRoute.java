package com.sparta.hubservice.hub_route.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub_route.domain.common.HaversineCalculator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub_route")
public class HubRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_id", nullable = false)
    private UUID hubRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="from_hub", nullable = false)
    private Hub fromHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="to_hub", nullable = false)
    private Hub toHub;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal distance;

    // 다이렉트로 가는 경로 생성시
    public HubRoute(Hub fromHub, Hub toHub, Long userId) {
        super(userId);
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.distance = this.calculateDistance();
        this.duration = this.calculateDuration();
    }

    // 체크포인트가 존재하는 최단 거리 생성시
    public HubRoute(Hub fromHub, Hub toHub, int duration, BigDecimal distance,  Long userId) {
        super(userId);
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.duration = duration;
        this.distance = distance;
    }

    // 거리 계산 (km)
    private BigDecimal calculateDistance() {
        return BigDecimal.valueOf(HaversineCalculator.haversineDistance(fromHub, toHub));
    }

    // 시간 계산 (분)
    private int calculateDuration(){
        double speed = 60.0;
        return (int) Math.round(this.distance.doubleValue() / speed);
    }

}
