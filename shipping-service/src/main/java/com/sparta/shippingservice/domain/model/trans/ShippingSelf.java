package com.sparta.shippingservice.domain.model.trans;
import com.sparta.shippingservice.domain.model.Shipping;
import com.sparta.shippingservice.domain.model.ShippingStatus;
import lombok.Builder;
import java.util.UUID;
@Builder
public record ShippingSelf(
        UUID orderId,
        String shippingAddress,
        String receiverName,
        UUID shippingManagerId,
        ShippingStatus status
) {
    public Shipping toShipping(Long userId) {
        return new Shipping(
                userId,
                null, // 배송 ID는 생성 시 자동 UUID 설정
                this.orderId,
                this.shippingAddress,
                this.receiverName,
                this.shippingManagerId,
                null, // routeLog는 추후 연결
                this.status != null ? this.status : ShippingStatus.PENDING // 기본값 설정
        );
    }
}