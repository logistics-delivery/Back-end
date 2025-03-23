package com.sparta.orderservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "name 필드는 필수입니다.")
    private String name;

    @NotNull(message = "supplierId 필드는 필수입니다.")
    private UUID supplierId;

    @NotNull(message = "receiverId 필드는 필수입니다.")
    private UUID receiverId;

    @NotNull(message = "productId 필드는 필수입니다.")
    private UUID productId;

    @NotNull(message = "totalPrice 필드는 필수입니다.")
    private BigDecimal totalPrice;

    private String requestDetail;

    @NotNull(message = "quantity는 필수입니다.") //
    private Integer quantity;
}
