package com.product.integrator.domain.repository;

import com.product.integrator.dto.ProductDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductRepository {


    CompletableFuture<ProductDto> getProductById(String productId);
    CompletableFuture<ProductDto> getProductByIdFallback(String productId, Exception e);

    CompletableFuture<List<String>> getIdProductSimilar(String productId);

    CompletableFuture<List<String>> getIdProductSimilarFallback(String productId, Exception e);
}
