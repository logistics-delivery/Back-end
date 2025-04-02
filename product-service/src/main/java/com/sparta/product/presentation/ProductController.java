package com.sparta.product.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.application.service.ProductService;
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

    private final ProductService productService;

    /**
     * 상품 생성
     */
    @Operation(summary = "Product 등록", description = "Product 생성 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto,
                                                                  @RequestHeader(value = "user_id", required = true) Long userId) {
        return ResponseEntity.ok(productService.createProduct(requestDto, userId));
    }


    /**
     * 상품 단일 조회
     */
    @Operation(summary = "Product 단일 조회", description = "Product 단일 조회 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping("/{productId}")
    public ResponseEntity<ReadProductResponseDto> readProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.readProduct(productId));
    }


    /**
     * 상품 목록 조회
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping
    public ResponseEntity<List<ReadProductResponseDto>> readAllProduct() {
        return ResponseEntity.ok(productService.readAllProduct());
    }


    /**
     * 상품 수정
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PutMapping("/{productId}")
    public ResponseEntity<UpdateProductResponseDto> updateProduct(@PathVariable UUID productId,
                                                                  @RequestBody UpdateProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.updateProduct(
                UpdateProductServiceRequestDto.of(requestDto, productId)));
    }


    /**
     * 상품 삭제
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId,
                                              @RequestHeader(value = "user_id", required = true) Long userId) {
        productService.deleteProduct(
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
        return ResponseEntity.ok(productService.searchProducts(requestDto, pageable));
    }




}
