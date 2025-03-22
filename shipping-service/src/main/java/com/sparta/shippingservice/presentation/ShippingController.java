package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.dto.request.CreateRouteLogRequestDto;
import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.request.CreateShippingWithRouteRequestDto;
import com.sparta.shippingservice.application.dto.request.UpdateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.dto.response.ShippingRouteResponseDto;
import com.sparta.shippingservice.application.service.ShippingRouteService;
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
    private final ShippingRouteService shippingRouteService;

    @PostMapping() // 배송 생성
    public ResponseEntity<ShippingResponseDto> Shipping(@Valid @RequestBody CreateShippingWithRouteRequestDto request) {
        ShippingResponseDto responseDto = shippingService.create(request.shipping(),request.routeLog());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}") // 특정 배송 정보 조회
    public ResponseEntity<ShippingResponseDto> getShippingById(@PathVariable("id") UUID id) {
        ShippingResponseDto responseDto = shippingService.getShippingById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping() //모든 배송 내역 조회
    public ResponseEntity<List<ShippingResponseDto>> getAllShipping() {
        List<ShippingResponseDto> allShipping = shippingService.getAllShipping();
        return ResponseEntity.ok(allShipping);
    }


    @PatchMapping("/{id}") // 배송 내역 수정
    public ResponseEntity<ShippingResponseDto> updateShipping(@PathVariable("id") UUID id, @Valid @RequestBody UpdateShippingRequestDto request) {
        ShippingResponseDto ResponseDto = shippingService.updateShipping(id, request);
        return ResponseEntity.ok(ResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShippingResponseDto> deleteHub(@PathVariable("id") UUID id, @RequestParam long userId) {
        ShippingResponseDto responseDto = shippingService.deleteShipping(id, userId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/route") // 배송 생성
    public ResponseEntity<ShippingRouteResponseDto> ShippingRoute(@Valid @RequestBody CreateRouteLogRequestDto requestDto) {
        ShippingRouteResponseDto responseDto = shippingRouteService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }


}
