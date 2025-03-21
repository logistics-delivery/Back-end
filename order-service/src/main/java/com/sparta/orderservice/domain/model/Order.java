package com.sparta.orderservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;  // 주문 ID

    @Column(nullable = false, length = 100)
    private String name;  // 주문명

    @Column(nullable = false)
    private UUID supplierId;  // 공급업체 ID

    @Column(nullable = false)
    private UUID receiverId;  // 수령업체 ID

    @Column(nullable = false)
    private UUID productId;  // 상품 ID


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;  // 주문 총 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;  // 주문 상태

    @Lob
    private String requestDetail;  // 요청 사항

    @Lob
    private String cancelReason;  // 주문 취소 사유


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 아이템 1:N 관계 설정

    public void setCreatedBy(Long createdBy) {
        super.update(createdBy);
    }


    // 주문 생성자 (BaseEntity의 createdBy 강제 설정)
    public Order(String name, UUID supplierId, UUID receiverId, UUID productId, BigDecimal totalPrice, String requestDetail) {
        super(0L); // createdBy 기본값 0L (임시 사용자)
        this.name = name;
        this.supplierId = supplierId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.requestDetail = requestDetail;
    }


    // 주문 update 메서드
    public void updateOrderDetails(String name, UUID supplierId, UUID receiverId, UUID productId, BigDecimal totalPrice, String requestDetail) {
        this.name = name;
        this.supplierId = supplierId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.requestDetail = requestDetail;
    }
    // 주문 soft 삭제
    public void softDelete() {
        super.delete(0L); // 사용자 인증 시스템 없으므로 임시로 0L 사용
    }

}

