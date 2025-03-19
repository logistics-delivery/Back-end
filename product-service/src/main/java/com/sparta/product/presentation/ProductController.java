package com.sparta.product.presentation;

import com.sparta.product.application.service.ProductServiceImpl;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.product.presentation.dto.response.ReadProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    /**
     * 상품 생성
     */
    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto,
                                                                  @RequestHeader(value = "X-User-Id", required = true) Long userId) {
        return ResponseEntity.ok(productServiceImpl.createProduct(requestDto, userId));
    }


    /**
     * 상품 단일 조회
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ReadProductResponseDto> readProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productServiceImpl.readProduct(productId));
    }
}
