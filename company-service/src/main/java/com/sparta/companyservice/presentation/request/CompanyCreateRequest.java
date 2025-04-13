package com.sparta.companyservice.presentation.request;

import com.sparta.companyservice.application.dto.CompanyCreateDto;
import com.sparta.companyservice.domain.model.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CompanyCreateRequest(
        @NotBlank @Size(min = 3) String name,
        @NotNull CompanyType type,
        @NotNull UUID hubId,
        @NotBlank String address
) {
    public CompanyCreateDto toDto() {
        return new CompanyCreateDto(name, type, hubId, address);
    }
}

// 레이어드 아키텍처 설계 헷갈리는 부분: CompanyCreateRequest가 application 계층의 dto가 아니라 왜 presentation 계층?
// ㄴ CompanyCreateRequest는 클라이언트에서 컨트롤러로 들어오는 HTTP 요청을 담는 객체이기 때문에 presentation 계층에 있어야 한다.
// ㄴ Controller 외부와 맞닿은 영역이라 presentation 계층에 두는 게 맞다고 함


/// 표: 계층 / 역할 / DTO 예시
/// presentation / HTTP 요청,응답 처리 (Request/Response) / CompanyCreateRequest
/// application / 서비스 로직, UseCase 정의 / CompanyDto (비즈니스 응답 DTO)
/// domain / 비즈니스 도메인 모델, Entity / 여긴 DTO 없음


// application 계층의 dto는:
// ㄴ Service 계층이 처리 결과를 반환할 때 사용하는 응답 DTO
// ㄴ 내부 도메인 객체(Company)를 변환해서 API 응답 포맷에 맞게 구성
// ㄴ 클라이언트에게 전달할 구조로 정제된 정보
