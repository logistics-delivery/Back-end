package com.sparta.shippingmanager.domain.model.trnas;

import com.sparta.shippingmanager.domain.model.ManagerType;
import com.sparta.shippingmanager.domain.model.ShippingManager;

import java.util.UUID;

public record ShippingManagerSelf(
        UUID shippingManagerId,
        ManagerType managerType,
        Boolean isActive,
        Integer count
)
{
    public ShippingManager toShippingManager(Long userId){
        ShippingManager manager = new ShippingManager(
                userId,
                this.shippingManagerId,
                this.managerType,
                this.isActive,
                this.count

        );
        manager.increaseCount(1);
        return manager;
    }
        }
