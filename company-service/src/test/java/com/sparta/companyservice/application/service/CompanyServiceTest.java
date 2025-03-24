package com.sparta.companyservice.application.service;

import com.sparta.companyservice.application.dto.CompanyCreateDto;
import com.sparta.companyservice.application.dto.CompanyDto;
import com.sparta.companyservice.application.dto.CompanyUpdateDto;
import com.sparta.companyservice.domain.model.Company;
import com.sparta.companyservice.domain.model.CompanyType;
import com.sparta.companyservice.domain.repository.CompanyRepository;
import com.sparta.companyservice.infrastructure.client.HubClient;
import com.sparta.companyservice.infrastructure.client.dto.HubClientDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private HubClient hubClient;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void 회사_생성_정상작동() {
        // given
        UUID hubId = UUID.randomUUID();
        long userId = 1L;

        CompanyCreateDto 요청 = new CompanyCreateDto(
                "새로운회사",
                CompanyType.PRODUCER,
                hubId,
                "서울시 강남구"
        );

        HubClientDto 가짜허브 = new HubClientDto(hubId, "서울허브", "서울시 중구");

        Company 저장예정회사 = Company.create(
                요청.name(),
                요청.address(),
                요청.hubId(),
                요청.type(),
                userId
        );

        when(hubClient.getHubById(hubId)).thenReturn(가짜허브);
        when(companyRepository.save(any(Company.class))).thenReturn(저장예정회사);

        // when
        CompanyDto 결과 = companyService.createCompany(요청, userId);

        // then
        assertEquals("새로운회사", 결과.name());
        assertEquals(CompanyType.PRODUCER, 결과.type());
        assertEquals("서울시 강남구", 결과.address());
        assertEquals(hubId, 결과.hubId());
        assertEquals(userId, 결과.createdBy());

        verify(hubClient, times(1)).getHubById(hubId);
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void 허브가_없으면_예외처리() {
        // given
        UUID hubId = UUID.randomUUID();
        long userId = 1L;

        CompanyCreateDto 요청 = new CompanyCreateDto(
                "없는허브회사",
                CompanyType.RECEIVER,
                hubId,
                "대전 중구"
        );

        when(hubClient.getHubById(hubId)).thenThrow(new IllegalArgumentException("존재하지 않는 허브입니다."));

        // when & then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            companyService.createCompany(요청, userId);
        });

        assertEquals("존재하지 않는 허브입니다.", ex.getMessage());
        verify(hubClient, times(1)).getHubById(hubId);
        verify(companyRepository, never()).save(any());
    }

    @Test
    void 회사_정보_수정_정상작동() {
        // given
        UUID companyId = UUID.randomUUID();
        UUID newHubId = UUID.randomUUID();
        long userId = 99L;

        Company 기존회사 = Company.create(
                "기존이름", "기존주소", UUID.randomUUID(), CompanyType.PRODUCER, 1L
        );

        CompanyUpdateDto 수정요청 = new CompanyUpdateDto("수정된이름", newHubId, "수정된주소");

        when(companyRepository.findByIdAndDeletedAtIsNull(companyId)).thenReturn(Optional.of(기존회사));
        when(hubClient.getHubById(newHubId)).thenReturn(new HubClientDto(newHubId, "부산허브", "부산시"));

        // when
        CompanyDto 결과 = companyService.updateCompany(companyId, 수정요청, userId);

        // then
        assertEquals("수정된이름", 결과.name());
        assertEquals("수정된주소", 결과.address());
        assertEquals(newHubId, 결과.hubId());

        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void 회사_정보_일부수정_정상작동() {
        // given
        UUID companyId = UUID.randomUUID();
        UUID 기존HubId = UUID.randomUUID();
        long userId = 42L;

        Company 기존회사 = Company.create("오리지널", "강남구", 기존HubId, CompanyType.RECEIVER, 1L);
        CompanyUpdateDto 수정요청 = new CompanyUpdateDto("수정된이름", null, null); // 일부만 수정

        when(companyRepository.findByIdAndDeletedAtIsNull(companyId)).thenReturn(Optional.of(기존회사));

        // when
        CompanyDto 결과 = companyService.updateCompany(companyId, 수정요청, userId);

        // then
        assertEquals("수정된이름", 결과.name());
        assertEquals("강남구", 결과.address()); // 그대로 유지
        assertEquals(기존HubId, 결과.hubId()); // 그대로 유지
    }

    @Test
    void 회사_삭제_정상작동() {
        // given
        UUID companyId = UUID.randomUUID();
        long userId = 88L;

        Company 기존회사 = Company.create("삭제대상", "용산구", UUID.randomUUID(), CompanyType.RECEIVER, 2L);

        when(companyRepository.findByIdAndDeletedAtIsNull(companyId)).thenReturn(Optional.of(기존회사));

        // when
        companyService.deleteCompany(companyId, userId);

        // then
        assertNotNull(기존회사.getDeletedAt());
        assertEquals(userId, 기존회사.getDeletedBy());

        verify(companyRepository).save(any(Company.class));
    }

}
