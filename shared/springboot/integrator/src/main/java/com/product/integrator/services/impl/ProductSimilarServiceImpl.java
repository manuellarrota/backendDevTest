package com.product.integrator.services.impl;

import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import com.product.integrator.services.ProductSimilarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSimilarServiceImpl implements ProductSimilarService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductSimilarServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProductSimilar(String productId) {
        CompletableFuture<List<String>> completableFutureListIds = productRepository.getIdProductSimilar(productId);
        List<String> productsIdList;
        List<ProductDto> productSimilar = new ArrayList<>();
        if(completableFutureListIds == null){
            log.info("Not found similar ids to: " + productId);
            return productSimilar;
        }
        try {
            productsIdList = completableFutureListIds.get();
        } catch (Exception e) {
            log.error("error in service getting similar ids: " + e.getMessage());
            return productSimilar;
        }

        if(productsIdList != null && !productsIdList.isEmpty() ){
            log.debug("get objects for similar ids for:  " + productsIdList.size());
            productSimilar = productsIdList.parallelStream().map(
                    idString -> {
                        try {
                            CompletableFuture<ProductDto> completableFuture = productRepository.getProductById(idString);
                            if(completableFuture != null){
                                return completableFuture.get();
                            }
                            log.error("error getting product: " + idString);
                            return new ProductDto();
                        } catch (Exception e){
                            log.error("error getting product: " + idString + ", message: " + e.getMessage());
                            return new ProductDto();
                        }
                    }
            ).collect(Collectors.toList());
        }
        return productSimilar;
    }
}
