package com.sparta.product.application.service;

import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.product.presentation.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    /**
 * Creates a new product using the details provided in the request DTO.
 *
 * @param requestDto the DTO containing the information required to create the product
 * @return the response DTO containing the details of the newly created product
 */
CreateProductResponseDto createProduct(CreateProductRequestDto requestDto);

    /**
 * Retrieves details for the product with the specified identifier.
 *
 * @param productId the unique identifier of the product to retrieve
 * @return a DTO containing detailed information about the product
 */
ReadProductResponseDto readProduct(UUID productId);

    List<ReadProductResponseDto> readAllProduct();

    UpdateProductResponseDto updateProduct(UpdateProductServiceRequestDto serviceDto);

    void deleteProduct(DeleteProductServiceRequestDto serviceDto);

    Page<SearchProductResponseDto> searchProducts(SearchProductRequestDto requestDto, Pageable pageable);
}
