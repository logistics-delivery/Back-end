package com.sparta.product.infrastructure.repository;

import com.sparta.product.domain.model.Product;
import com.sparta.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

}
