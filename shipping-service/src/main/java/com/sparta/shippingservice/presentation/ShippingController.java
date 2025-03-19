package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.ShippingResponseDto;
import com.sparta.shippingservice.application.service.ShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ShippingResponseDto>Shipping(@Valid @RequestBody CreateShippingRequestDto requestDto) {
        ShippingResponseDto responseDto = shippingService.create(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}") // 특정 배송 정보 조회
    public ResponseEntity<ShippingResponseDto> getShippingById(@PathVariable UUID id){
        ShippingResponseDto responseDto = shippingService.getShippingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping() //모든 배송 내역 조회
    public ResponseEntity<List<ShippingResponseDto>> getAllShipping(){
        List<ShippingResponseDto> allShipping = shippingService.getAllShipping();
        return ResponseEntity.status(HttpStatus.OK).body(allShipping);
    }
}
