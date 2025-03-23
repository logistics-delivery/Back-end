package com.sparta.shippingmanager.application.dto.request;

import com.sparta.shippingmanager.domain.model.ManagerType;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.domain.model.trnas.ShippingManagerSelf;

import java.util.UUID;

public record ShippingManagerCreateRequestDto(
        UUID shippingManagerId,
        ManagerType managerType,
        Boolean isActive,
        Integer count
) {
    public ShippingManagerSelf of (){
        return new ShippingManagerSelf(
            this.shippingManagerId(),
            this.managerType(),
            this.isActive(),
            this.count()

        );
    }

}
