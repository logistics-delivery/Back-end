package com.sparta.companyservice.presentation.request;

import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import com.sparta.companyservice.domain.model.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CompanyUpdateRequest (
        @NotBlank @Size(min = 3) String name,
        @NotNull CompanyType type,
        @NotNull UUID hubId,
        @NotBlank String address
    ) {
    public CompanyUpdateDto toDto() {
        return new CompanyUpdateDto(name, type, hubId, address);
    }
}
