package com.sparta.product.domain.model;


import com.sparta.commonmodule.entity.BaseEntity;
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
     * Creates a new Product instance using the provided details.
     *
     * <p>This method constructs a Product by setting its name, description, price, display status,
     * and associated company identifier using the builder pattern.</p>
     *
     * @param name the product's name (up to 100 characters)
     * @param description the product's description (up to 255 characters)
     * @param price the product's price
     * @param isDisplay true if the product should be displayed; false otherwise
     * @param companyId the identifier of the company associated with the product
     * @return a new Product instance with the specified attributes
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
     * Updates the product's attributes.
     *
     * <p>This method assigns new values to the product's name, description, price, and display status and returns the updated instance.</p>
     *
     * @param name the new name for the product
     * @param description the new description for the product
     * @param price the new price for the product
     * @param isDisplay flag indicating whether the product should be displayed
     * @return the updated product instance
     */
    public Product updateProduct(String name, String description, BigDecimal price, boolean isDisplay) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isDisplay = isDisplay;
        return this;
    }


}