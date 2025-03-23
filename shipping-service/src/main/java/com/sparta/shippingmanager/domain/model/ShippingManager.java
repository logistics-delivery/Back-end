package com.sparta.shippingmanager.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "p_shipping_managers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingManager extends BaseEntity {

    @Id
    @Column(name = "shipping_managers_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID shippingManagerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ManagerType managerType;

    @Column(nullable = false)
    private Integer shippingOrder;

    @Column(nullable = false)
    private Boolean isActive;

    private Integer count =0;

    public void increaseCount(){
        this.count++;
    }


}











