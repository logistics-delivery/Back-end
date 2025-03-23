package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.dto.request.CreateShippingWithRouteRequestDto;
import com.sparta.shippingservice.application.dto.request.UpdateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingWithRouteResponseDto;
import com.sparta.shippingservice.application.service.ShippingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PostMapping() // 배송 생성
    public ResponseEntity<ShippingWithRouteResponseDto> create (@Valid @RequestBody CreateShippingWithRouteRequestDto request,
                                                                @RequestHeader("userId")Long userId) {
        ShippingWithRouteResponseDto responseDto = shippingService.create(request.shipping(), request.routeLog(),userId);
        return ResponseEntity.ok(responseDto);

    }

    @GetMapping("/{shippingId}") // 특정 배송 정보 조회
    public ResponseEntity<ShippingResponseDto> getShippingById(@PathVariable("shippingId") UUID id) {
        ShippingResponseDto responseDto = shippingService.getShippingById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping() //모든 배송 내역 조회
    public ResponseEntity<List<ShippingResponseDto>> getAllShipping() {
        List<ShippingResponseDto> allShipping = shippingService.getAllShipping();
        return ResponseEntity.ok(allShipping);
    }


    @PatchMapping("/{shippingId}") // 배송 내역 수정
    public ResponseEntity<ShippingResponseDto> updateShipping(@PathVariable("shippingId") UUID id, @Valid @RequestBody UpdateShippingRequestDto request, @RequestHeader("userId") Long userId ) {
        ShippingResponseDto ResponseDto = shippingService.updateShipping(id, request,userId);
        return ResponseEntity.ok(ResponseDto);
    }

    @DeleteMapping("/{shippingId}")
    public ResponseEntity<ShippingResponseDto> deleteShipping(@PathVariable("shippingId") UUID id, @RequestHeader("userId") long userId) {
        ShippingResponseDto responseDto = shippingService.deleteShipping(id, userId);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/{shippingId}/{shippingLogId}") // 특정 배송 로그 조회
    public ResponseEntity<ShippingRouteResponseDto> getLogById(@PathVariable("shippingId") UUID id, @PathVariable("shippingLogId") UUID logId) {
        ShippingRouteResponseDto responseDto = shippingService.getLogById(id, logId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/log")
    public ResponseEntity<List<ShippingRouteResponseDto>> getAllLog() {
        List<ShippingRouteResponseDto> allLog = shippingService.getLogAll();
        return ResponseEntity.ok(allLog);

    }


    @DeleteMapping("/{shippingId}/{shippingLogId}")
    public ResponseEntity<ShippingRouteResponseDto>deleteShippingLog(@PathVariable("shippingId") UUID id, @PathVariable("shippingLogId") UUID logId ,@RequestParam Long userId){
        ShippingRouteResponseDto responseDto = shippingService.deleteShippingLog(id, logId, userId);
        return ResponseEntity.ok(responseDto);

    }



}
