package com.product.integrator.domain.repository;

import com.product.integrator.dto.ProductDto;

import java.util.List;

public interface ProductRepository {

    ProductDto getProductById(String productId);

    List<String> getIdProductSimilar(String productId);
}
