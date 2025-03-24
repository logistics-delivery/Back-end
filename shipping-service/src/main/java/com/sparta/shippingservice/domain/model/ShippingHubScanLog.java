package com.sparta.shippingservice.domain.model;

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
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_shipping_hub_scan_log")
public class ShippingHubScanLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shipping_hub_scan_log_id")
    private UUID hubShippingScanLogId;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="shipping_id", nullable = false)
    private Shipping shippingId;

    @CreatedDate
    @Column(nullable = false,  updatable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private ShippingStatus status;

    @Column(name = "next_hub_id", nullable = true)
    private UUID nextHubId;

    public ShippingHubScanLog(UUID hubId,Shipping shippingId, ShippingStatus status, Long userId) {
        super(userId);
        this.hubId = hubId;
        this.shippingId = shippingId;
        this.status = status;
    }

    public ShippingHubScanLog(UUID hubId,Shipping shippingId, ShippingStatus status, UUID nextHubId, Long userId) {
        super(userId);
        this.hubId = hubId;
        this.shippingId = shippingId;
        this.status = status;
        this.nextHubId = nextHubId;
    }

    public static ShippingHubScanLog createInboundLog(UUID hubId, Shipping shippingId, Long userId) {
        return new ShippingHubScanLog(hubId,shippingId, ShippingStatus.INBOUND, userId);
    }

    public static ShippingHubScanLog createOutboundLog(UUID hubId, Shipping shippingId, Long userId) {
        return new ShippingHubScanLog(hubId, shippingId, ShippingStatus.OUTBOUND, userId);
    }

    public enum ShippingStatus {
        INBOUND, OUTBOUND
    }

}
