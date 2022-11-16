package com.product.integrator.client.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductRestClientImpl implements ProductRestClient {


    @Override
    public ProductDto getProductById(Long productId) {

        return new ProductDto();
    }

    @Override
    public List<Long> getIdProductSimilar(Long productId) {
        return new ArrayList<>();
    }
}
