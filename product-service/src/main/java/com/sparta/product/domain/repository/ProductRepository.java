package com.sparta.product.domain.repository;

import com.sparta.product.domain.model.Product;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.SearchProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByIdAndHubId(UUID productId, UUID hubId);

    List<Product> findAll();

    Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto, Pageable pageable);
}
