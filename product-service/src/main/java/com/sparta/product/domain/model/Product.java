package com.sparta.product.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("is_deleted IS FALSE")
@Builder(access = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String name;

    @Column(name = "product_description", nullable = false, length = 255)
    private String description;

    @Column(name = "product_price", nullable = false)
    private BigDecimal price;

    @Column(name = "product_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_display", nullable = false)
    private boolean isDisplay;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;


    /**
     * 상품 생성
     */
    public static Product createProduct(CreateProductRequestDto requestDto, Long userId) {
        return Product.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .price(requestDto.price())
                .quantity(requestDto.quantity())
                .isDisplay(requestDto.isDisplay())
                .companyId(requestDto.companyId())
                .hubId(requestDto.hubId())
                .build();
    }


    /**
     * 상품 수정
     */
    public Product updateProduct(UpdateProductServiceRequestDto serviceDto) {
        this.name = serviceDto.name();
        this.description = serviceDto.description();
        this.price = serviceDto.price();
        this.isDisplay = serviceDto.isDisplay();
        return this;
    }


    /**
     *  상품 수정(재고 감소)
     */
    public void decreaseQuantity(Integer quantity) {
        validateDecreaseQuantity(quantity);
        this.quantity = this.quantity - quantity;
    }



    /**
     *  상품 수정(재고 증가)
     */
    public void increaseQuantity(Integer quantity) {
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




    // DTO -> Entity 변환 메서드
    public static Product of(CreateProductResponseDto responseDto) {
        return Product.builder()
                .name(responseDto.name())
                .description(responseDto.description())
                .quantity(responseDto.quantity())
                .price(responseDto.price())
                .isDisplay(responseDto.isDisplay())
                .companyId(responseDto.companyId())
                .hubId(responseDto.hubId())
                .build();
    }


}