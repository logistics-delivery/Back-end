package com.sparta.shippingservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Entity
@Table(name = "p_shipping_checkpoint")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShippingCheckpoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderIndex;

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    private String hubName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_route_id")
    private ShippingRouteLog shippingRouteLog;

    public void setShippingRoute(ShippingRouteLog route) {
        this.shippingRouteLog = route;
    }
}