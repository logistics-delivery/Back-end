package com.sparta.product.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import com.sparta.product.infrastructure.client.CompanyClient;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyClient companyClient;


    /**
     * Creates a new product.
     *
     * Validates that the specified company exists, then creates and saves a product using the details
     * provided in the request DTO. Returns a response DTO representing the newly created product.
     *
     * @param requestDto DTO containing the product's name, description, price, display flag, and company ID.
     * @return a response DTO representing the created product
     * @throws ResourceNotFoundException if the company does not exist
     */
    @Override
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        validateCompanyExists(requestDto.companyId());
        Product product = productRepository
                .save(Product.createProduct(
                        requestDto.name(),
                        requestDto.description(),
                        requestDto.price(),
                        requestDto.isDisplay(),
                        requestDto.companyId()
                ));

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
     * Updates an existing product's details.
     *
     * <p>This method locates the product using the ID provided in the update DTO, updates its name,
     * description, price, and display flag with the new values, and returns a response DTO reflecting
     * the updated product information. A ResourceNotFoundException is thrown if the product cannot be found.</p>
     *
     * @param serviceDto the DTO containing the product ID and updated attribute values
     * @return a response DTO with the updated product information
     * @throws ResourceNotFoundException if no product with the specified ID exists
     */
    @Override
    public UpdateProductResponseDto updateProduct(UpdateProductServiceRequestDto serviceDto) {
        Product product = productRepository.findById(serviceDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 상품 입니다."));
        product.updateProduct(
                serviceDto.name(),
                serviceDto.description(),
                serviceDto.price(),
                serviceDto.isDisplay()
        );

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


    /**
     * Validates that a company with the specified identifier exists.
     * <p>
     * If the company is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param companyId the unique identifier of the company to validate
     * @throws ResourceNotFoundException if no company exists with the given identifier
     */
    private void validateCompanyExists(UUID companyId) {
        if (!companyClient.existsById(companyId)) {
            throw new ResourceNotFoundException("해당 업체가 존재하지 않습니다.");
        }
    }


}
