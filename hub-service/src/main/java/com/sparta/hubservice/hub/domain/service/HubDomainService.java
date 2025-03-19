package com.sparta.hubservice.hub.domain.service;

// 도메인 계층: 오직 순수한 값만 받아서 도메인 로직을 수행해야 함
// (순수 비즈니스 규칙 실행, 객체 생성/검증/저장 책임)

import com.sparta.hubservice.hub.domain.model.Hub;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HubDomainService {

    // 허브 생성
    public Hub createHub(long userId,  String name, String address, BigDecimal latitude,  BigDecimal longitude) {
        return Hub.builder()
            .name(name)
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .userId(userId)
            .build();
    }

    // 허브 수정
    public void updateHub(Hub hub, long userId, String address, BigDecimal latitude, BigDecimal longitude) {
        hub.updateHub(address, latitude, longitude, userId);
    }

    // 허브삭제 (Soft Delete)
    public void deleteHub(Hub hub, long userId) {
        hub.delete(userId);
    }

}
