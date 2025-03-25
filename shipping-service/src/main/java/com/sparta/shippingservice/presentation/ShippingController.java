package com.sparta.shippingservice.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.shippingservice.application.dto.request.*;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingWithRouteResponseDto;
import com.sparta.shippingservice.application.service.ShippingService;

import com.sparta.shippingservice.domain.model.Shipping;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @Operation(summary = "배송 등록",description = "배송 등록 API 입니다")
    @RoleCheck("ROLE_MASTER")
    @PostMapping() // 배송 생성
    public ResponseEntity<ShippingWithRouteResponseDto> create (@Valid @RequestBody CreateShippingRequestDto request, @RequestHeader("userId")Long userId) {
        ShippingWithRouteResponseDto responseDto = shippingService.create(request,userId);
        return ResponseEntity.ok(responseDto);

    }
    @Operation(summary = "특정 배송 조회",description = "특정 배송 조회API 입니다")
    @GetMapping("/{shippingId}") // 특정 배송 정보 조회
    public ResponseEntity<ShippingResponseDto> getShippingById(@PathVariable("shippingId") UUID id) {
        ShippingResponseDto responseDto = shippingService.getShippingById(id);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "모든 배송 조회",description = "모든 배송 조회API 입니다")
    @GetMapping() //모든 배송 내역 조회
    public ResponseEntity<List<ShippingResponseDto>> getAllShipping() {
        List<ShippingResponseDto> allShipping = shippingService.getAllShipping();
        return ResponseEntity.ok(allShipping);
    }

    @Operation(summary = "배송 수정",description = "배송 수정 API 입니다")
    @RoleCheck("ROLE_MASTER")
    @PatchMapping("/{shippingId}") // 배송 내역 수정
    public ResponseEntity<ShippingResponseDto> updateShipping(@PathVariable("shippingId") UUID id, @Valid @RequestBody UpdateShippingRequestDto request, @RequestHeader("userId") Long userId ) {
        ShippingResponseDto ResponseDto = shippingService.updateShipping(id, request,userId);
        return ResponseEntity.ok(ResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShippingResponseDto>> searchShippings(@ModelAttribute ShippingSearchCondition condition, HttpServletRequest request) {
        Page<ShippingResponseDto> result = shippingService.searchShipping(condition);
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "배송 삭제",description = "배송 삭제 API 입니다")
    @RoleCheck("ROLE_MASTER")
    @DeleteMapping("/{shippingId}")
    public ResponseEntity<ShippingResponseDto> deleteShipping(@PathVariable("shippingId") UUID id, @RequestHeader("userId") long userId) {
        ShippingResponseDto responseDto = shippingService.deleteShipping(id, userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 배송 로그 조회",description = "특정 배송 로그 조회 API 입니다")
    @GetMapping("/{shippingId}/{shippingLogId}") // 특정 배송 로그 조회
    public ResponseEntity<ShippingRouteResponseDto> getLogById(@PathVariable("shippingId") UUID id, @PathVariable("shippingLogId") UUID logId) {
        ShippingRouteResponseDto responseDto = shippingService.getLogById(id, logId);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "모든 배송 로그 조회",description = "모든 배송 로그 조회 API 입니다")
    @GetMapping("/log")
    public ResponseEntity<List<ShippingRouteResponseDto>> getAllLog() {
        List<ShippingRouteResponseDto> allLog = shippingService.getLogAll();
        return ResponseEntity.ok(allLog);

    }

    @Operation(summary = "배송 로그 삭제",description = "배송 로그 삭제 API 입니다")
    @RoleCheck("ROLE_MASTER")
    @DeleteMapping("/{shippingId}/{shippingLogId}")
    public ResponseEntity<ShippingRouteResponseDto>deleteShippingLog(@PathVariable("shippingId") UUID id, @PathVariable("shippingLogId") UUID logId ,@RequestParam Long userId){
        ShippingRouteResponseDto responseDto = shippingService.deleteShippingLog(id, logId, userId);
        return ResponseEntity.ok(responseDto);

    }
    @Operation(summary = "모든 배송 로그 검색",description = "모든 배송 로그 검색 API 입니다")
    @GetMapping("/log/search")
    public ResponseEntity<Page<ShippingRouteResponseDto>> searchShippingRoutes(
            @ModelAttribute ShippingRouteSearchCondition condition
    ) {
        Page<ShippingRouteResponseDto> result = shippingService.searchRoutes(condition);
        return ResponseEntity.ok(result);
    }


}
