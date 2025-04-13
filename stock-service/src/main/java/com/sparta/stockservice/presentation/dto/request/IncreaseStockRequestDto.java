package com.sparta.stockservice.presentation.dto.request;

import java.util.UUID;

public record IncreaseStockRequestDto(UUID companyId,
                                      UUID hubId,
                                      Integer quantity
) {


}
