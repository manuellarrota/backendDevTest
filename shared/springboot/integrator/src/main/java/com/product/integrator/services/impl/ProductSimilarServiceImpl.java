package com.product.integrator.services.impl;

import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import com.product.integrator.services.ProductSimilarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSimilarServiceImpl implements ProductSimilarService {

    private ProductRepository productRepository;

    @Autowired
    public ProductSimilarServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProductSimilar(String productId) {
        log.info("Getting similar products for: %s" , productId);
        List<String> productsIdList = productRepository.getIdProductSimilar(productId);
        List<ProductDto> productSimilar = new ArrayList<>();
        if(productsIdList != null && !productsIdList.isEmpty() ){
            log.info("get objects: %d " , productsIdList.size());
            productSimilar = productsIdList.stream().parallel().map(
                    idString -> productRepository.getProductById(idString)
            ).collect(Collectors.toList());
        }
        return productSimilar;
    }
}
