package com.product.integrator.client;

import com.product.integrator.dto.ProductDto;

import java.util.List;

public interface ProductRestClient {

    ProductDto getProductById(Long productId);

    List<Long> getIdProductSimilar(Long productId);

}
