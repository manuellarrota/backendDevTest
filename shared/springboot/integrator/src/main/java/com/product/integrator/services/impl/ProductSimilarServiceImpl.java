package com.product.integrator.services.impl;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.services.ProductSimilarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSimilarServiceImpl implements ProductSimilarService {

    @Override
    public List<ProductDto> getProductSimilar(String productId) {
        return new ArrayList<>();
    }
}
