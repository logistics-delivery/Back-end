package com.sparta.product.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
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

    @Column(name = "is_display", nullable = false)
    private boolean isDisplay;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;


    /**
     * 상품 생성
     */
    public static Product createProduct(String name, String description, BigDecimal price, boolean isDisplay,UUID companyId) {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .isDisplay(isDisplay)
                .companyId(companyId)
                .build();
    }


    /**
     * 상품 수정
     */
    public Product updateProduct(String name, String description, BigDecimal price, boolean isDisplay) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isDisplay = isDisplay;
        return this;
    }



    // DTO -> Entity 변환 메서드
    public static Product of(CreateProductResponseDto responseDto) {
        return Product.builder()
                .name(responseDto.name())
                .description(responseDto.description())
                .price(responseDto.price())
                .isDisplay(responseDto.isDisplay())
                .companyId(responseDto.companyId())
                .build();
    }
}