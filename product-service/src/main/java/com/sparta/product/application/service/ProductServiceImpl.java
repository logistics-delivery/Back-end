package com.sparta.product.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.product.presentation.dto.response.ReadProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    /**
     * 상품 생성
     */
    @Override
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId) {
        Product product = productRepository.save(Product.createProduct(requestDto, userId));
        return CreateProductResponseDto.from(product);
    }


    /**
     * 상품 단일 조회
     */
    @Override
    @Transactional(readOnly = true)
    public ReadProductResponseDto readProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 상품 입니다."));
        return ReadProductResponseDto.from(product);
    }
}
