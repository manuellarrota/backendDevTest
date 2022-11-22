package com.product.integrator.domain.repository.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
@Slf4j
public class ProductRestRepositoryImpl implements ProductRepository {

    private final ProductRestClient productRestClient;

    @Autowired
    public ProductRestRepositoryImpl(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    @TimeLimiter(name = "productService", fallbackMethod = "getProductByIdFallback")
    @Cacheable(cacheNames = "products")
    @Override
    public CompletableFuture<ProductDto> getProductById(String productId) {
        log.debug("getting product to: " + productId);
        return CompletableFuture.supplyAsync(() -> productRestClient.getProductById(productId));
    }

    @TimeLimiter(name = "productService", fallbackMethod = "getIdProductSimilarFallback")
    @Override
    public CompletableFuture<ProductDto> getProductByIdFallback(String productId, Exception e){
        log.debug("fallback para " + productId + " por " + e.getMessage());
        return CompletableFuture.completedFuture(new ProductDto(productId,"",0.0, false));
    }


    @Override
    @Cacheable(cacheNames = "product_ids")
    public CompletableFuture<List<String>> getIdProductSimilar(String productId) {
        log.debug("getting similar ids to: " + productId);
        return CompletableFuture.supplyAsync(() -> productRestClient.getIdProductSimilar(productId));
    }

    @Override
    public CompletableFuture<List<String>> getIdProductSimilarFallback(String productId, Exception e) {
        log.debug("Fallback para " + productId + " por " + e.getMessage());
        return CompletableFuture.completedFuture(new ArrayList<>());
    }
}
