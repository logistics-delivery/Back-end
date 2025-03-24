package com.sparta.shippingservice.application.dto.response;

import com.sparta.shippingservice.domain.model.Shipping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingSearchResult {
    private List<Shipping> content;
    private int page;          // 현재 페이지 번호
    private int pageSize;      // 한 페이지에 몇 건
    private long totalCount;   // 전체 개수
}