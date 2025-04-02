package com.sparta.stockservice.presentation.dto.request;

import java.util.UUID;

public record DecreaseStockRequestDto(UUID companyId,
                                      UUID hubId,
                                      Integer quantity
) {


}
