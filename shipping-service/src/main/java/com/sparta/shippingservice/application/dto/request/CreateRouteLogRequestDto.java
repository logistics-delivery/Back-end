package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.trans.RouteLogSelf;
import com.sparta.shippingservice.domain.model.Shipping;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateRouteLogRequestDto(

        Shipping shipping,


        @NotNull(message = "출발 허브 ID는 필수입니다.")
        UUID startHubId,

        @NotNull(message = "도착 허브 ID는 필수입니다.")
        UUID endHubId,

        @NotNull(message = "배송 순번은 필수입니다.")
        @Min(value = 1, message = "배송 순번은 1 이상이어야 합니다.")
        Integer sequence,

        @NotNull(message = "예상 거리 입력은 필수입니다.")
        BigDecimal estimatedDistance,

        @NotNull(message = "예상 시간 입력은 필수입니다.")
        @Min(value = 1, message = "예상 시간은 1분 이상이어야 합니다.")
        Integer estimatedTime,

        BigDecimal actualDistance,  // 실측 데이터는 선택값일 수 있음

        Integer actualTime,         // 마찬가지로 선택값

        @NotNull(message = "배송 담당자 ID는 필수입니다.")
        UUID shippingManagerId
) {
    public RouteLogSelf of() {
        return new RouteLogSelf(
                this.startHubId(),
                this.endHubId(),
                this.sequence(),
                this.estimatedDistance(),
                this.estimatedTime(),
                this.actualDistance(),
                this.actualTime(),
                this.shippingManagerId()
        );
    }

}