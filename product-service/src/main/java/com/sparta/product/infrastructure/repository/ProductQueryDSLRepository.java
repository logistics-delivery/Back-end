package com.sparta.product.infrastructure.repository;

import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.SearchProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryDSLRepository {

  Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto, Pageable pageable);

}
