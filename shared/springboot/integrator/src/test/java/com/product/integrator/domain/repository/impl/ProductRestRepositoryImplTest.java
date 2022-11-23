package com.product.integrator.domain.repository.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class ProductRestRepositoryImplTest {

    @Mock
    private ProductRestClient productRestClient;

    private ProductRepository productRepository;
    private final ProductDto productDto = new ProductDto("1","Shirt", 9.99, true );
    private final ProductDto productDtoFallback = new ProductDto("1","",0.0, false );
    private final CompletableFuture<ProductDto> completableFuture = CompletableFuture.completedFuture(productDto);
    private final CompletableFuture<ProductDto> completableFutureFallback = CompletableFuture.completedFuture(productDtoFallback);

    @BeforeEach
    public void setup(){
        // Mocks productRestClient
        Mockito.when(productRestClient.getProductById("1")).thenReturn(
                productDto
        );

        // Mocks productRestClient
        Mockito.when(productRestClient.getIdProductSimilar("1")).thenReturn(
                Arrays.asList("2","3","4")
        );

        productRepository = new ProductRestRepositoryImpl(productRestClient);
    }

    @Test
    void when_getProductById_all_ok_then_return_a_product() {
        CompletableFuture<ProductDto> _completableFuture = productRepository.getProductById("1");
        try {
            Assertions.assertThat(_completableFuture.get()).isEqualTo(completableFuture.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void when_getProductByIdFallback_all_ok_then_return_a_product() {
        CompletableFuture<ProductDto> _completableFuture = productRepository.getProductByIdFallback("1", new RuntimeException());
        try {
            Assertions.assertThat(_completableFuture.get()).isEqualTo(completableFutureFallback.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void when_getIdProductSimilar_all_ok_then_return_a_List() {
        CompletableFuture<List<String>> listCompletableFuture = productRepository.getIdProductSimilar("1");
        try {
            Assertions.assertThat(listCompletableFuture.get()).hasSize(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void when_getIdProductSimilarFallback_all_ok_then_return_a_empty_list() {
        CompletableFuture<List<String>> listCompletableFuture = productRepository.getIdProductSimilarFallback("1", new RuntimeException());
        try {
            Assertions.assertThat(listCompletableFuture.get()).isEmpty();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}