package com.sparta.hubservice.hub.domain.model;

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
@Table(name = "p_hub_shipping_scan_log")
public class HubShippingScanLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_shipping_scan_log_id")
    private Long hubShippingScanLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="hub_id", nullable = false)
    private Hub hub;

    @Column(name = "shipping_id", nullable = false)
    private UUID shippingId;

    @CreatedDate
    @Column(nullable = false,  updatable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private ShippingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_hub_id", nullable = true)
    private Hub nextHub;

    public HubShippingScanLog(Hub hub,UUID shippingId, ShippingStatus status, Long userId) {
        super(userId);
        this.hub = hub;
        this.shippingId = shippingId;
        this.status = status;
    }

    public HubShippingScanLog(Hub hub,UUID shippingId, ShippingStatus status, Hub nextHub, Long userId) {
        super(userId);
        this.hub = hub;
        this.shippingId = shippingId;
        this.status = status;
        this.nextHub = nextHub;
    }

    public static HubShippingScanLog createInboundLog(Hub hub, UUID shippingId, Long userId) {
        return new HubShippingScanLog(hub,shippingId, ShippingStatus.INBOUND, userId);
    }

    public static HubShippingScanLog createOutboundLog(Hub hub, UUID shippingId, Hub nextHub, Long userId) {
        return new HubShippingScanLog(hub, shippingId, ShippingStatus.OUTBOUND, nextHub, userId);
    }


    public enum ShippingStatus {
        INBOUND, OUTBOUND
    }

}
