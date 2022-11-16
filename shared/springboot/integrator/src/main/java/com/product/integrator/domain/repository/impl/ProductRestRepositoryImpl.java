package com.product.integrator.domain.repository.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductRestRepositoryImpl implements ProductRepository {

    private ProductRestClient productRestClient;

    @Autowired
    public ProductRestRepositoryImpl(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    @Override
    public ProductDto getProductById(String productId) {
        return productRestClient.getProductById(productId);
    }

    @Override
    public List<String> getIdProductSimilar(String productId) {
        return productRestClient.getIdProductSimilar(productId);
    }
}
