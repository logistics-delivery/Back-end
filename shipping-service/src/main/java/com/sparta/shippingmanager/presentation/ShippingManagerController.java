package com.sparta.shippingmanager.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerSearchCondition;
import com.sparta.shippingmanager.application.service.ShippingManagerService;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import com.sparta.shippingmanager.infrastructure.ShippingManagerSearchRepository;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shipping-managers")
@RequiredArgsConstructor
public class ShippingManagerController {

    private final ShippingManagerService shippingManagerService;

    private final ShippingManagerSearchRepository shippingManagerRepository;


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
    @Operation(summary = "배송 담당자 검색",description = "배송 담당자 검색 API 입니다")
    @RoleCheck("ROLE_HUB,ROLE_MASTER,ROLE_SHIPPING")
    @GetMapping("/search")
    public ResponseEntity<Page<ShippingManagerResponseDto>> search(@ModelAttribute ShippingManagerSearchCondition condition) {
        Page<ShippingManagerResponseDto> result = shippingManagerService.search(condition);
        return ResponseEntity.ok(result);
    }

}
