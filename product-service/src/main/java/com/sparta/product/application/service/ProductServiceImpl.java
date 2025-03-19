package com.sparta.product.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.product.presentation.dto.response.ReadProductResponseDto;
import com.sparta.product.presentation.dto.response.UpdateProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


    /**
     * 상품 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReadProductResponseDto> readAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(ReadProductResponseDto::from)
                .toList();
    }


    /**
     * 상품 수정
     */
    @Override
    public UpdateProductResponseDto updateProduct(UpdateProductServiceRequestDto serviceDto) {
        Product product = productRepository.findById(serviceDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 상품 입니다."));
        product.updateProduct(serviceDto);
        return UpdateProductResponseDto.from(product);
    }
}
