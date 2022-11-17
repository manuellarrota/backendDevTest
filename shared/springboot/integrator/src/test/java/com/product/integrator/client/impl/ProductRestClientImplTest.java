package com.product.integrator.client.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ProductRestClientImplTest {
    @Mock
    private RestTemplate restTemplate;
    private ProductRestClient productRestClient;

    @Value("${url.get_product_by_id}")
    private String urlGetProductById;
    @Value("${url.get_product_similar_id}")
    private String urlGetProductSimilarId;

    @BeforeEach
    public void setup(){

        String productId = "1";
        String productUrl = urlGetProductById +productId;
        String productListUrl = urlGetProductSimilarId.replace("{productId}", productId);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        ProductDto productDto = new ProductDto("1","Shirt", 9.99, true );
        List<ProductDto> productsSimilarMock = new ArrayList<>();
        productsSimilarMock.add(productDto);
        productResponseDto.setSimilarProducts(productsSimilarMock);
        Mockito.when(restTemplate.getForEntity(productUrl, ProductDto.class)).thenReturn(
                new ResponseEntity<>(productDto, HttpStatus.OK)
        );
        Mockito.when(restTemplate.getForEntity(productListUrl, List.class)).thenReturn(
                new ResponseEntity<>(Arrays.asList("2","3","4"), HttpStatus.OK)
        );

        productRestClient = new ProductRestClientImpl(urlGetProductById, urlGetProductSimilarId, restTemplate );

    }
    @Test
    void getProductById_when_all_ok_then_returns_ok() {
        ProductDto productDto = productRestClient.getProductById("1");
        Assertions.assertThat(productDto.getName()).isEqualTo("Shirt");
    }

    @Test
    void getProductById_when_productId_not_found_then_return_null() {
        ProductDto productDto = productRestClient.getProductById("2");
        Assertions.assertThat(productDto.getName()).isNull();
    }
    @Test
    void getProductById_when_productId_is_null_then_return_null() {
        ProductDto productDto = productRestClient.getProductById(null);
        Assertions.assertThat(productDto.getName()).isNull();
    }

    @Test
    void getIdProductSimilar_when_all_ok_then_return_ok() {
       List<String> idStringList =  productRestClient.getIdProductSimilar("1");
        Assertions.assertThat(idStringList).hasSize(3);
    }

    @Test
    void getIdProductSimilar_when_not_found_then_return_new_list() {
        List<String> idStringList =  productRestClient.getIdProductSimilar("2");
        Assertions.assertThat(idStringList).isEmpty();
    }
    @Test
    void getIdProductSimilar_when_is_null_then_return_new_list() {
        List<String> idStringList =  productRestClient.getIdProductSimilar(null);
        Assertions.assertThat(idStringList).isEmpty();
    }
}