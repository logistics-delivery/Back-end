package com.sparta.product.presentation;

import com.sparta.product.application.service.ProductServiceImpl;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    /**
     *  상품 생성
     */
    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto,
                                                                  @RequestHeader(value = "X-User-Id", required = true) Long userId) {
        return ResponseEntity.ok(productServiceImpl.createProduct(requestDto, userId));
    }
}
