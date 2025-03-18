package com.sparta.product.application.service;

import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    /**
     *  상품 생성
     */
    @Override
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId) {
        Product product = productRepository.save(Product.createProduct(requestDto, userId));
        return CreateProductResponseDto.from(product);
    }
}
