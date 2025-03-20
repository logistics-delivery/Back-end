package com.sparta.companyservice.presentation.response;

import java.util.UUID;

public record CompanyDeleteResponse (
        UUID id,
        String Message
) {
    public static CompanyDeleteResponse of(UUID id) {
        return new CompanyDeleteResponse(id, "업체가 성공적으로 삭제되었습니다.");
    }
}
