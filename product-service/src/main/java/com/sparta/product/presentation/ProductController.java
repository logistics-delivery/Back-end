package com.sparta.product.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.DecreaseProductQuantityServiceRequestDto;
import com.sparta.product.application.dto.IncreaseProductQuantityServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.application.service.ProductServiceImpl;
import com.sparta.product.presentation.dto.request.*;
import com.sparta.product.presentation.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Service", description = "상품 서비스 API")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    /**
     * 상품 생성
     */
    @Operation(summary = "Product 등록", description = "Product 생성 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto,
                                                                  @RequestHeader(value = "user_id", required = true) Long userId) {
        return ResponseEntity.ok(productServiceImpl.createProduct(requestDto, userId));
    }


    /**
     * 상품 단일 조회
     */
    @Operation(summary = "Product 단일 조회", description = "Product 단일 조회 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping("/{productId}")
    public ResponseEntity<ReadProductResponseDto> readProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productServiceImpl.readProduct(productId));
    }


    /**
     * 상품 목록 조회
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping
    public ResponseEntity<List<ReadProductResponseDto>> readAllProduct() {
        return ResponseEntity.ok(productServiceImpl.readAllProduct());
    }


    /**
     * 상품 수정
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PutMapping("/{productId}")
    public ResponseEntity<UpdateProductResponseDto> updateProduct(@PathVariable UUID productId,
                                                                  @RequestBody UpdateProductRequestDto requestDto) {
        return ResponseEntity.ok(productServiceImpl.updateProduct(
                UpdateProductServiceRequestDto.of(requestDto, productId)));
    }


    /**
     * 상품 삭제
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId,
                                              @RequestHeader(value = "user_id", required = true) Long userId) {
        productServiceImpl.deleteProduct(
                DeleteProductServiceRequestDto.of(userId, productId));
        return ResponseEntity.noContent().build();
    }


    /**
     * 상품 검색
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping("/search")
    public ResponseEntity<Page<SearchProductResponseDto>> searchProducts(@ModelAttribute SearchProductRequestDto requestDto,
                                                                         Pageable pageable) {
        return ResponseEntity.ok(productServiceImpl.searchProducts(requestDto, pageable));
    }


    /**
     *  상품 수정(재고 감소)
     */
    @PutMapping("/{productId}/decrease")
    public ResponseEntity<DecreaseProductQuantityResponseDto> decreaseProductQuantity(@PathVariable UUID productId,
                                                                                      @RequestBody DecreaseProductQuantityRequestDto requestDto) {
        return ResponseEntity.ok(productServiceImpl.decreaseProductQuantity(
                DecreaseProductQuantityServiceRequestDto.of(requestDto, productId)));
    }


    /**
     *  상품 수정(재고 증가)
     */
    @PutMapping("/{productId}/increase")
    public ResponseEntity<IncreaseProductQuantityResponseDto> increaseProductQuantity(@PathVariable UUID productId,
                                                                                      @RequestBody IncreaseProductQuantityRequestDto requestDto) {
        return ResponseEntity.ok(productServiceImpl.increaseProductQuantity(
                IncreaseProductQuantityServiceRequestDto.of(requestDto, productId)));
    }

}
