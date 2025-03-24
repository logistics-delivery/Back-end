//package com.sparta.commonmodule.config;
//
//import java.util.Optional;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//

// 각 모듈에 시큐리티 적용 후 적용 예정

//public class AuditorAwareImpl implements AuditorAware<Long> {
//
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof String idStr) {
//            try {
//                return Optional.of(Long.parseLong(idStr));
//            } catch (NumberFormatException e) {
//                return Optional.empty();
//            }
//        } else if (principal instanceof Long idLong) {
//            return Optional.of(idLong);
//        }
//
//        return Optional.empty();
//    }
//}
