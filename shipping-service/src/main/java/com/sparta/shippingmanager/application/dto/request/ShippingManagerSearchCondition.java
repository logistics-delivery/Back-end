package com.sparta.shippingmanager.application.dto.request;

import com.sparta.shippingmanager.domain.model.ManagerType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingManagerSearchCondition {

    private UUID shippingManagerId;
    private ManagerType managerType;
    private Integer shippingOrder;
    private String sortBy;
    private int pageSize; // 허용 값: 10, 30, 50
    private int page;
}