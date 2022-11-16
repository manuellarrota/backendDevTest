package com.product.integrator.services.impl;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.services.ProductSimilarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductSimilarServiceImpl implements ProductSimilarService {

    @Override
    public ArrayList<ProductDto> getProductSimilar(Long productId) {
        return new ArrayList<>();
    }
}
