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
        ProductDto productDto = new ProductDto("1","Shirt", 9.99, true );
        Mockito.when(productRepository.getProductById("1")).thenReturn(
                CompletableFuture.completedFuture(productDto)
        );
        Mockito.when(productRepository.getIdProductSimilar("1")).thenReturn(
                CompletableFuture.completedFuture(Arrays.asList("2","3","4"))
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
    void getProductSimilar_when_productId_is_null_ok_then_return_ok(){
        List<ProductDto> productDtoList = productSimilarService.getProductSimilar(null);
        Assertions.assertThat(productDtoList).isEmpty();
    }

}