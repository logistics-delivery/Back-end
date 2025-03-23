package com.sparta.shippingservice.application.dto.request;

import com.sparta.shippingservice.domain.model.trans.ShippingSelf;
import com.sparta.shippingservice.domain.model.ShippingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.UUID;

public record CreateShippingRequestDto(
    // TODO  CREATE 요청은 언제 발생 ? 주문이 등록될떄 ? ORDER - SERVICE 에게 해당 정보 요청
    @NotNull(message = "주문 ID는 필수입니다.")
    UUID orderId,

    @NotNull(message = "배송 주소는 필수입니다.")
    @Size(min = 5, max = 255, message = "배송 주소는 5~255자 사이여야 합니다.")
    String shippingAddress,

    @NotNull(message = "수령인 이름은 필수입니다.")
    @Size(min = 2, max = 100, message = "수령인 이름은 2~100자 사이여야 합니다.")
    String receiverName,


    @NotNull(message = "배송 상태는 필수입니다.")
    ShippingStatus status

) {
    public ShippingSelf of( UUID managerId){
        return new ShippingSelf(
            this.orderId(),
            this.shippingAddress(),
            this.receiverName(),
                managerId, //외부에서 받아온 값 주입
            this.status()

        );
    }

}