package com.product.integrator.controller;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import com.product.integrator.services.ProductSimilarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProductControllerTest {

    @Mock
    private ProductSimilarService productSimilarService;
    private ProductController productController;

    @BeforeEach
    public void setup(){
        productController = new ProductController(productSimilarService);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        ProductDto productDto = new ProductDto("1","Shirt", 9.99, true );
        List<ProductDto> productsSimilarMock = new ArrayList<>();
        productsSimilarMock.add(productDto);
        productResponseDto.setSimilarProducts(new ArrayList<>());
        Mockito.when(productSimilarService.getProductSimilar("1")).thenReturn(
                productsSimilarMock
        );
        Mockito.when(productSimilarService.getProductSimilar("0")).thenReturn(
                new ArrayList<>()
        );
    }

    @Test
    void getProductSimilar() {
        ResponseEntity<ProductResponseDto>productResponseDto = productController.getProductSimilar("1");
        Assertions.assertThat(productResponseDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getProductSimilar_Id_not_found_then_not_found_error() {
        ResponseEntity<ProductResponseDto> productResponseDto = productController.getProductSimilar("0");
        Assertions.assertThat(productResponseDto.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}