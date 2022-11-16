package com.product.integrator.client;

import com.product.integrator.dto.ProductDto;

import java.util.List;

public interface ProductRestClient {

    ProductDto getProductById(String productId);

    List<String> getIdProductSimilar(String productId);

}
