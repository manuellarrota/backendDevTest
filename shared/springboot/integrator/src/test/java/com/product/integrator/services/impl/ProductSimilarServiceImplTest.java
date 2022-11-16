package com.product.integrator.services.impl;

import com.product.integrator.domain.repository.ProductRepository;
import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import com.product.integrator.services.ProductSimilarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@SpringBootTest
class ProductSimilarServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private ProductSimilarService productSimilarService;

    @BeforeEach
    public void setup(){

        ProductResponseDto productResponseDto = new ProductResponseDto();
        ProductDto productDto = new ProductDto("1","Shirt", 9.99, true );
        List<ProductDto> productsSimilarMock = new ArrayList<>();
        productsSimilarMock.add(productDto);
        productResponseDto.setSimilarProducts(new ArrayList<>());
        Mockito.when(productRepository.getProductById("1")).thenReturn(
                productDto
        );
        Mockito.when(productRepository.getIdProductSimilar("1")).thenReturn(
                Arrays.asList("2","3","4")
        );
        productSimilarService = new ProductSimilarServiceImpl(productRepository);
    }

    @Test
    void getProductSimilar_when_all_ok_then_return_ok(){
        List<ProductDto> productDtos = productSimilarService.getProductSimilar("1");
        Assertions.assertThat(productDtos).hasSize(3);
    }

    @Test
    void getProductSimilar_when_productId_not_found_ok_then_return_ok(){
        List<ProductDto> productDtos = productSimilarService.getProductSimilar("0");
        Assertions.assertThat(productDtos).isEmpty();
    }

    @Test
    void getProductSimilar_when_productId_is_null_ok_then_return_ok(){
        List<ProductDto> productDtos = productSimilarService.getProductSimilar(null);
        Assertions.assertThat(productDtos).isEmpty();
    }

}