package com.sparta.shippingmanager.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.application.service.ShippingManagerService;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "업체 배송 담당자 지정",description = "업체 배송 담당자 지정 API 입니다")
    @GetMapping("/assign")  // 배송 담당자 할당
    public ShippingManager assignManager(){
        return shippingManagerService.assign();
    }


    @Operation(summary = "배송 담당자 등록",description = "배송 담당자 등록 API 입니다")
    @RoleCheck("ROLE_HUB,ROLE_MASTER")
    @PostMapping()
    public ResponseEntity<ShippingManagerResponseDto> create(@Valid @RequestBody ShippingManagerCreateRequestDto request, @RequestHeader("userId") Long userId){
        ShippingManagerResponseDto response = shippingManagerService.create(request, userId);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "특정 배송 담당자 조회",description = "특정 배송 담당자 조회 API 입니다")
    @RoleCheck("ROLE_HUB,ROLE_MASTER")
    @GetMapping("/{id}")
    public ResponseEntity<ShippingManagerResponseDto> getById(@PathVariable("id") UUID id){
        ShippingManagerResponseDto response = shippingManagerService.getById(id);
        return ResponseEntity.ok(response);
    }


}
