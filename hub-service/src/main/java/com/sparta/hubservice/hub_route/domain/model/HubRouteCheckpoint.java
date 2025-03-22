package com.sparta.hubservice.hub_route.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.hubservice.hub.domain.model.Hub;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub_route_checkpoint")
public class HubRouteCheckpoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_checkpoint_id",  nullable = false)
    private UUID hubRouteCheckpointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="hub_route_id", nullable = false)
    private HubRoute hubRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="checkpoint_hub_id", nullable = false)
    private Hub checkpointHubId;

    @Column(nullable = false)
    private int sequence;

    @Builder
    public HubRouteCheckpoint(HubRoute hubRoute, Hub checkpointHub, int sequence, long userId) {
        super(userId);
        this.hubRouteId = hubRoute;
        this.checkpointHubId = checkpointHub;
        this.sequence = sequence;
    }

}
