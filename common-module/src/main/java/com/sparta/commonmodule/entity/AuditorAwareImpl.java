package com.sparta.commonmodule.entity;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Spring Data JPA의 AuditorAware 구현체
 * HTTP 요청의 `X-User-Id` 헤더 값을 기반으로 @CreatedBy, @LastModifiedBy 값을 자동 설정
 */
@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    /**
     * 현재 요청을 보낸 사용자의 ID를 반환 (Auditing 기능에서 호출됨)
     * @return Optional<Long> - 요청에서 추출한 사용자 ID (없으면 null)
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        Long userId = getUserIdFromHeader();
        return Optional.ofNullable(userId);
    }

    /**
     * HTTP 요청에서 'X-User-Id' 헤더 값을 가져와 Long 타입으로 변환
     * @return Long - 변환된 사용자 ID (없으면 null)
     */
    private Long getUserIdFromHeader() {
        // 현재 요청의 정보를 가져옴
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String userIdHeader = request.getHeader("user_id"); // 헤더에서 사용자 ID 가져오기

            if (userIdHeader != null) {
                try {
                    return Long.parseLong(userIdHeader); // String → Long 변환
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}