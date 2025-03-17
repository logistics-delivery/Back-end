package com.sparta.hubservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
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
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub_route")
public class HubRoute extends BaseEntity {

    @Id
    @Column(name = "hub_route_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
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

    public HubRoute(Hub fromHub, Hub toHub, int duration, BigDecimal distance, long userId) {
        super(userId);
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.duration = duration;
        this.distance = distance;
    }



}
