package com.sparta.product.application.service;

import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;


public interface ProductService {

  CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId);

}
