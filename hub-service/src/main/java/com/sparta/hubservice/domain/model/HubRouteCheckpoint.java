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
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub_route_checkpoint")
public class HubRouteCheckpoint extends BaseEntity {

    @Id
    @Column(name = "hub_route_checkpoint_id",  nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID hubRouteCheckpointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="hub_route_id", nullable = false)
    private HubRoute hubRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="checkpoint_hub_id", nullable = false)
    private Hub checkpointHubId;

    @Column(nullable = false)
    private int sequence;

    public HubRouteCheckpoint(HubRoute hubRoute, Hub checkpointHub, int sequence, long userId) {
        super(userId);
        this.hubRouteId = hubRoute;
        this.checkpointHubId = checkpointHub;
        this.sequence = sequence;
    }

}
