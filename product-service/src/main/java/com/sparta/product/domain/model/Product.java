package com.sparta.product.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
     *  상품 생성
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