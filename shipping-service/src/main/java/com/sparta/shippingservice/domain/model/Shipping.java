package com.sparta.shippingservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_shipping") // 테이블 명 지정
public class Shipping extends BaseEntity {

    @Id
    @Column(name = "shipping_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @Column(name = "receiver_name", nullable = false, length = 100)
    private String receiverName;

    @Column(name = "shipping_manager_id", nullable = false)
    private UUID shippingManagerId;

    @OneToOne(mappedBy = "shipping",cascade = CascadeType.PERSIST)
    private ShippingRouteLog routeLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ShippingStatus status = ShippingStatus.PENDING; // 기본값 설정

    public Shipping(Long userId, UUID id, UUID orderId, String shippingAddress, String receiverName, UUID shippingManagerId, ShippingRouteLog routeLog, ShippingStatus status) {
        super(userId);
        this.id = id;
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.receiverName = receiverName;
        this.shippingManagerId = shippingManagerId;
        this.routeLog = routeLog;
        this.status = status;
    }

    public Shipping updateShipping(Shipping shipping, Long userId) {
        super.update(userId);
        if (this.status == ShippingStatus.DELIVERED) {
            throw new IllegalStateException("배송이 완료된 후에는 정보를 변경할 수 없습니다.");
        }
       if(shipping.getStatus() !=null) this.status=shipping.getStatus();
       if(shipping.getOrderId()!=null) this.orderId = shipping.getOrderId();
       if(shipping.getShippingAddress()!=null) this.shippingAddress=shipping.getShippingAddress();
       if(shipping.getReceiverName()!=null) this.receiverName = shipping.getReceiverName();
       return this;
    }

    public void add(ShippingRouteLog routeLog) {
        this.routeLog = routeLog;
    }



    @PrePersist
    public void prePersist(){
        if(this.id == null){
            this.id = UUID.randomUUID();
        }
        // this.getCreatedAt() =LocalDateTime.now();
    }


}
