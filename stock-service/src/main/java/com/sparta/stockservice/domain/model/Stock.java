package com.sparta.stockservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(
        name="p_stock",
        uniqueConstraints =  @UniqueConstraint(columnNames = {"product_id", "hub_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("is_deleted IS FALSE")
@Builder(access = AccessLevel.PRIVATE)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="stock_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    /**
     *  재고 감소
     */
    public void decreaseStock(Integer quantity) {
        validateDecreaseQuantity(quantity);
        this.quantity = this.quantity - quantity;
    }



    /**
     *  재고 증가
     */
    public void increaseStock(Integer quantity) {
        this.quantity = this.quantity + quantity;
    }


    private void validateDecreaseQuantity(Integer quantity) {
        if (quantity == null || quantity < 30) {
            throw new IllegalArgumentException("최소 30개 이상 요청해야 합니다.");
        }
        if (this.quantity < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }

}
