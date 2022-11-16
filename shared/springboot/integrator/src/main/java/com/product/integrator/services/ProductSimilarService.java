package com.product.integrator.services
        ;

import com.product.integrator.dto.ProductDto;

import java.util.List;

public interface ProductSimilarService {

    List<ProductDto> getProductSimilar(String productId);
}
