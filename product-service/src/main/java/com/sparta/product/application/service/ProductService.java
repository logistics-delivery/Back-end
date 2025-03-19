package com.sparta.product.application.service;

import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.product.presentation.dto.response.ReadProductResponseDto;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId);

    ReadProductResponseDto readProduct(UUID productId);

    List<ReadProductResponseDto> readAllProduct();
    
}
