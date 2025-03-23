package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingSearchCondition {
    private String shippingAddress;
    private String receiverName;
    private ShippingStatus status;
    private String sortBy;     // "createdAt", "modifiedAt"
    private int pageSize;      // 10, 30, 50만 허용
    private int page;          // 0부터 시작
}











