package com.product.integrator.controller;

import com.product.integrator.services.ProductSimilarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductControllerTest {

    @Mock
    private ProductSimilarService productSimilarService;
    private ProductController productController;

    @BeforeEach
    public void setup(){
        productController = new ProductController(productSimilarService);
        //Mockito.when(productSimilarService.getProductSimilar(applicationDate, productId, brandId)).thenReturn(
        //        price
        //);
    }

    @Test
    void getProductSimilars() {

    }
}