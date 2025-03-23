package com.sparta.product.presentation.dto.request;

import java.util.UUID;

public record DecreaseProductQuantityRequestDto(UUID companyId,
                                                UUID hubId,
                                                Integer quantity
) {


}
