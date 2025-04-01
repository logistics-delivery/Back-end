package com.sparta.product.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import com.sparta.product.infrastructure.client.CompanyClient;
import com.sparta.product.infrastructure.client.HubClient;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final HubClient hubClient;
    private final CompanyClient companyClient;


    /**
     * 상품 생성
     */
    @Override
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId) {
        validateCompanyExists(requestDto.companyId());
        validateHubExists(requestDto.hubId());
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


    /**
     * 상품 삭제
     */
    @Override
    public void deleteProduct(DeleteProductServiceRequestDto serviceDto) {
        Product product = productRepository.findById(serviceDto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 상품 입니다."));
        product.delete(serviceDto.userId());
    }


    /**
     * 상품 검색
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto, Pageable pageable) {
        return productRepository.searchProducts(requestDto, pageable);
    }


    // 업체 존재 검증 메서드
    private void validateCompanyExists(UUID companyId) {
        if (!companyClient.existsById(companyId)) {
            throw new ResourceNotFoundException("해당 업체가 존재하지 않습니다.");
        }
    }


    // 허브 존재 검증 메서드
    private void validateHubExists(UUID hubId) {
        Optional.ofNullable(hubClient.getHubById(hubId))
                .orElseThrow(() -> new ResourceNotFoundException("해당 허브가 존재하지 않습니다."));
    }

}
