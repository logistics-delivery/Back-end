package com.sparta.commonmodule.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {


    @Before("@annotation(roleCheck)")  // @RoleCheck 어노테이션을 가진 메서드에 AOP 적용
    public void checkPermission(JoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        // 요구되는 권한 (예: ROLE_MASTER)
        String[] requiredRole = roleCheck.value().split(",");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String currentUserRole = request.getHeader("role");//현재 로그인한 사용자의 권한
        boolean check = false;
        for (String requiredRoleName : requiredRole) {
            if (requiredRoleName.equals(currentUserRole)) {
                check = true;
            }
        }
        if (!check) {
            log.error("CurrentUserRole [{}] is not matched requiredRole [{}]", currentUserRole, requiredRole);
            throw new RuntimeException("권한이 다릅니다.");
        }

    }

}
