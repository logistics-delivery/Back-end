package com.sparta.shippingmanager.presentation;

import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.application.service.ShippingManagerService;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shipping-managers")
@RequiredArgsConstructor
public class ShippingManagerController {

    private final ShippingManagerService shippingManagerService;

    @GetMapping("/assign")  // 배송 담당자 할당
    public ShippingManager assignManager(){
        return shippingManagerService.assign();
    }

    @PostMapping()
    public ResponseEntity<ShippingManagerResponseDto> create(@Valid @RequestBody ShippingManagerCreateRequestDto request, @RequestHeader("userId") Long userId){
        ShippingManagerResponseDto response = shippingManagerService.create(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingManagerResponseDto> getById(@PathVariable("id") UUID id){
        ShippingManagerResponseDto response = shippingManagerService.getById(id);
        return ResponseEntity.ok(response);
    }


}
