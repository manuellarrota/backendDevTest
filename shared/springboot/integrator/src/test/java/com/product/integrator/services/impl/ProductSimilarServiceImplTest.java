package com.product.integrator.services.impl;

import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import com.product.integrator.services.ProductSimilarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
class ProductSimilarServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private ProductSimilarService productSimilarService;

    @BeforeEach
    public void setup(){
        ProductDto productDto1 = new ProductDto("1","Shirt", 9.99, true );
        ProductDto productDto2 = new ProductDto("2","Shirt", 9.99, true );
        ProductDto productDto3 = new ProductDto("3","Shirt", 9.99, true );
        ProductDto productDto4 = new ProductDto("4","Shirt", 9.99, true );

        CompletableFuture<ProductDto> completableFutureProductDtoError =  new CompletableFuture<>();
        completableFutureProductDtoError.completeExceptionally(new Exception("communication error"));

        CompletableFuture<List<String>> completableFutureListError =  new CompletableFuture<>();
        completableFutureListError.completeExceptionally(new Exception("communication error"));

        // Mocks getProductById
        Mockito.when(productRepository.getProductById("1")).thenReturn(
                CompletableFuture.completedFuture(productDto1)
        );
        Mockito.when(productRepository.getProductById("2")).thenReturn(
                CompletableFuture.completedFuture(productDto2)
        );
        Mockito.when(productRepository.getProductById("3")).thenReturn(
                CompletableFuture.completedFuture(productDto3)
        );
        Mockito.when(productRepository.getProductById("4")).thenReturn(
                CompletableFuture.completedFuture(productDto4)
        );
        Mockito.when(productRepository.getProductById("null")).thenReturn(
                CompletableFuture.completedFuture(null)
        );
        Mockito.when(productRepository.getProductById("error")).thenReturn(
                completableFutureProductDtoError
        );

        // Mocks getIdProductSimilar
        Mockito.when(productRepository.getIdProductSimilar("1")).thenReturn(
                CompletableFuture.completedFuture(Arrays.asList("2","3","4"))
        );
        Mockito.when(productRepository.getIdProductSimilar("some_null")).thenReturn(
                CompletableFuture.completedFuture(Arrays.asList(null,"3","4"))
        );
        Mockito.when(productRepository.getIdProductSimilar("some_error")).thenReturn(
                CompletableFuture.completedFuture(Arrays.asList("error","3","4"))
        );
        Mockito.when(productRepository.getIdProductSimilar("error")).thenReturn(
                completableFutureListError
        );

        productSimilarService = new ProductSimilarServiceImpl(productRepository);
    }

    @Test
    void getProductSimilar_when_all_ok_then_return_ok(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar("1");
        Assertions.assertThat(productDtoList).hasSize(3);
    }

    @Test
    void getProductSimilar_when_productId_not_found_ok_then_return_ok(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar("0");
        Assertions.assertThat(productDtoList).isEmpty();
    }

    @Test
    void getProductSimilar_when_productId_is_null_then_return_ok(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar(null);
        Assertions.assertThat(productDtoList).isEmpty();
    }

    @Test
    void getProductSimilar_when_productId_is_null_from_list_then_return_some_empty(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar("some_null");
        Assertions.assertThat(productDtoList).hasSize(3);
        Assertions.assertThat(productDtoList.get(0).getName()).isNull();
    }

    @Test
    void getProductSimilar_when_productId_is_error_from_list_then_return_some_empty(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar("some_error");
        Assertions.assertThat(productDtoList).hasSize(3);
        Assertions.assertThat(productDtoList.get(0).getName()).isNull();
    }

    @Test
    void getProductSimilar_when_repository_return_error_then_return_ok(){
        try{
            productSimilarService.getProductSimilar("error");
        }catch (Exception error){
            Assertions.fail("communication error");
        }
    }

}