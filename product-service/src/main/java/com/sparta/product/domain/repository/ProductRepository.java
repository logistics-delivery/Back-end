package com.sparta.product.domain.repository;

import com.sparta.product.domain.model.Product;

public interface ProductRepository {

    Product save(Product product);
}
