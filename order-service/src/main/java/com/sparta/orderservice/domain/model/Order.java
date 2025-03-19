package com.sparta.orderservice.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 주문 생성 시간

    @Column(nullable = false)
    private UUID createdBy;  // 주문자 ID

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // 수정 시간

    private UUID updatedBy;  // 수정자 ID

    private LocalDateTime deletedAt;  // 삭제 시간

    private UUID deletedBy;  // 삭제자 ID

    @Lob
    private String cancelReason;  // 주문 취소 사유


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 아이템 1:N 관계 설정


    // 주문 update 메서드
    public void updateOrderDetails(String name, UUID supplierId, UUID receiverId, UUID productId, BigDecimal totalPrice, String requestDetail) {
        this.name = name;
        this.supplierId = supplierId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.requestDetail = requestDetail;
    }


}

