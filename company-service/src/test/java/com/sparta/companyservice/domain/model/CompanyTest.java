package com.sparta.companyservice.domain.model;

import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void 회사_정상_생성됨() {
        // given
        String name = "테스트회사";
        String address = "서울시 강남구";
        UUID hubId = UUID.randomUUID();
        CompanyType type = CompanyType.PRODUCER;
        long userId = 1L;

        // when
        Company company = Company.create(name, address, hubId, type, userId);

        // then
        assertEquals(name, company.getName());
        assertEquals(address, company.getAddress());
        assertEquals(hubId, company.getHubId());
        assertEquals(type, company.getType());
        assertEquals(userId, company.getCreatedBy());
    }

    @Test
    void 회사명_null_이면_예외발생() {
        // given
        String name = null;
        String address = "서울시 중구";
        UUID hubId = UUID.randomUUID();
        CompanyType type = CompanyType.RECEIVER;
        long userId = 1L;

        // when & then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Company.create(name, address, hubId, type, userId)
        );
        assertTrue(ex.getMessage().contains("업체명"));
    }

    @Test
    void 회사정보_업데이트_정상작동() {
        // given
        Company company = Company.create(
                "기존회사",
                "기존주소",
                UUID.randomUUID(),
                CompanyType.RECEIVER,
                1L
        );

        String newName = "새로운회사";
        String newAddress = "새로운주소";
        UUID newHubId = UUID.randomUUID();
        long updatedBy = 2L;

        // when
        company.update(newName, newAddress, newHubId, updatedBy);

        // then
        assertEquals(newName, company.getName());
        assertEquals(newAddress, company.getAddress());
        assertEquals(newHubId, company.getHubId());
        assertEquals(updatedBy, company.getUpdatedBy());
    }

    @Test
    void 회사정보_일부만_업데이트() {
        // given
        UUID hubId = UUID.randomUUID();
        Company company = Company.create(
                "기존상호",
                "기존주소",
                hubId,
                CompanyType.PRODUCER,
                1L
        );

        CompanyUpdateDto dto = new CompanyUpdateDto("바뀐상호", null, null);
        long updatedBy = 3L;

        // when
        company.applyUpdate(dto, updatedBy);

        // then
        assertEquals("바뀐상호", company.getName());
        assertEquals("기존주소", company.getAddress());
        assertEquals(hubId, company.getHubId());
        assertEquals(updatedBy, company.getUpdatedBy());
    }

    @Test
    void 회사정보_소프트삭제_정상작동() {
        // given
        Company company = Company.create(
                "삭제할회사",
                "삭제주소",
                UUID.randomUUID(),
                CompanyType.RECEIVER,
                1L
        );

        long deletedBy = 5L;

        // when
        company.delete(deletedBy);

        // then
        assertNotNull(company.getDeletedAt());
        assertEquals(deletedBy, company.getDeletedBy());
    }
}
