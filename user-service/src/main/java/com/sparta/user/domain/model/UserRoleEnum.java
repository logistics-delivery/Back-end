package com.sparta.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    MASTER("ROLE_MASTER"), // 마스터 권한
    HUB("ROLE_HUB"), // 허브 권한
    SHIPPING("ROLE_SHIPPING"), // 배송 권한
    COMPANY("ROLE_COMPANY"); // 업체 권한(디폴트)


    private final String authority;
}
