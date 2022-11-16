package com.product.integrator.services
        ;

import com.product.integrator.dto.ProductDto;

import java.util.ArrayList;

public interface ProductSimilarService {

    ArrayList<ProductDto> getProductSimilar(Long productId);
}
