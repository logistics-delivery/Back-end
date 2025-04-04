package com.sparta.product.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import com.sparta.product.application.dto.DeleteProductServiceRequestDto;
import com.sparta.product.application.dto.UpdateProductServiceRequestDto;
import com.sparta.product.application.service.ProductService;
import com.sparta.product.presentation.dto.request.*;
import com.sparta.product.presentation.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Service", description = "상품 서비스 API")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new product.
     *
     * <p>This endpoint accepts a product creation request and returns the details of the newly created product.
     * The product information is provided via the request body, and a valid "user_id" header is required.</p>
     *
     * @param requestDto the details of the product to create
     * @param userId the identifier of the user making the request (provided in the "user_id" header)
     * @return a {@code ResponseEntity} containing the created product details as a {@link CreateProductResponseDto}
     */
    @Operation(summary = "Product 등록", description = "Product 생성 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto,
                                                                  @RequestHeader(value = "user_id", required = true) Long userId) {
        return ResponseEntity.ok(productService.createProduct(requestDto));
    }


    /**
     * Retrieves a product by its unique identifier.
     *
     * <p>This endpoint returns the details of the product corresponding to the provided UUID.
     * Accessible to users with roles: ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, or ROLE_SHIPPING.</p>
     *
     * @param productId the UUID of the product to fetch
     * @return a ResponseEntity containing a ReadProductResponseDto with the product's details
     */
    @Operation(summary = "Product 단일 조회", description = "Product 단일 조회 api 입니다.")
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping("/{productId}")
    public ResponseEntity<ReadProductResponseDto> readProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.readProduct(productId));
    }


    /**
     * Retrieves a list of all available products.
     *
     * @return a ResponseEntity containing a list of ReadProductResponseDto objects representing the available products.
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping
    public ResponseEntity<List<ReadProductResponseDto>> readAllProduct() {
        return ResponseEntity.ok(productService.readAllProduct());
    }


    /**
     * Updates an existing product.
     *
     * <p>This endpoint updates a product identified by its UUID with the provided update details.</p>
     *
     * @param productId the unique identifier of the product to update
     * @param requestDto the details to update the product
     * @return a ResponseEntity containing the updated product information
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY")
    @PutMapping("/{productId}")
    public ResponseEntity<UpdateProductResponseDto> updateProduct(@PathVariable UUID productId,
                                                                  @RequestBody UpdateProductRequestDto requestDto) {
        return ResponseEntity.ok(productService.updateProduct(
                UpdateProductServiceRequestDto.of(requestDto, productId)));
    }


    /**
     * Deletes a product based on its unique identifier.
     *
     * <p>This endpoint removes the product identified by its UUID from the system. It requires a valid
     * user ID from the "user_id" request header and is accessible only by users with the ROLE_MASTER or
     * ROLE_HUB roles.</p>
     *
     * @param productId the unique identifier of the product to delete
     * @param userId the ID of the user performing the deletion
     * @return a ResponseEntity with no content upon successful deletion
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId,
                                              @RequestHeader(value = "user_id", required = true) Long userId) {
        productService.deleteProduct(
                DeleteProductServiceRequestDto.of(userId, productId));
        return ResponseEntity.noContent().build();
    }


    /**
     * Searches for products using the specified search criteria and pagination settings.
     *
     * @param requestDto the search criteria for filtering products
     * @param pageable the pagination and sorting information for the results
     * @return a ResponseEntity containing a page of SearchProductResponseDto objects that match the criteria
     */
    @RoleCheck("ROLE_MASTER, ROLE_HUB, ROLE_COMPANY, ROLE_SHIPPING")
    @GetMapping("/search")
    public ResponseEntity<Page<SearchProductResponseDto>> searchProducts(@ModelAttribute SearchProductRequestDto requestDto,
                                                                         Pageable pageable) {
        return ResponseEntity.ok(productService.searchProducts(requestDto, pageable));
    }




}
