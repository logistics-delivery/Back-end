package com.sparta.shippingservice.presentation;

import com.sparta.shippingservice.application.dto.request.CreateShippingRequestDto;
import com.sparta.shippingservice.application.dto.response.CreateShippingResponseDto;
import com.sparta.shippingservice.application.service.ShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shippings")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;
    @PostMapping()
    public ResponseEntity<CreateShippingResponseDto>Shipping(@Valid @RequestBody CreateShippingRequestDto requestDto) {
        CreateShippingResponseDto responseDto = shippingService.create(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
