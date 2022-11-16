package com.product.integrator.domain.repository.impl;

import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductRestRepositoryImpl implements ProductRepository {

    @Override
    public ProductDto getProductById(Long productId) {
        return new ProductDto();
    }

    @Override
    public List<Long> getIdProductSimilar(Long productId) {
        return new ArrayList<>();
    }
}
