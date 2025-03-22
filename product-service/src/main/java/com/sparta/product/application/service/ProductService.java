package com.sparta.product.application.service;

import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.product.presentation.dto.response.ReadProductResponseDto;
import com.sparta.product.presentation.dto.response.SearchProductResponseDto;
import com.sparta.product.presentation.dto.response.UpdateProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId);

    ReadProductResponseDto readProduct(UUID productId);

    List<ReadProductResponseDto> readAllProduct();

    UpdateProductResponseDto updateProduct(UpdateProductServiceRequestDto serviceDto);

    void deleteProduct(DeleteProductServiceRequestDto serviceDto);

    Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto, Pageable pageable);
}
