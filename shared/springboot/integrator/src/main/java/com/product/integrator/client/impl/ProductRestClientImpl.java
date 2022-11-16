package com.product.integrator.client.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductRestClientImpl implements ProductRestClient {
    // load values from properties file
    @Value("${url.get_product_by_id}")
    private String urlGetProductById;
    @Value("${url.get_product_similar_id}")
    private String urlGetProductSimilarId;
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public ProductDto getProductById(String productId) {
        log.debug("Getting product detail for " + productId);
        String productUrl = urlGetProductById +productId;
        ResponseEntity<ProductDto> response;
        try{
            response = restTemplate.getForEntity(productUrl, ProductDto.class);
            if ( response.getStatusCode() != HttpStatus.OK){
                log.debug("error consuming similar ids: " + response);
                return new ProductDto();
            }
        }catch (RestClientException error){
            log.error("http error: " + error.getMessage());
            return new ProductDto();
        }
        log.debug("Getting product detail response:" + response);
        return response.getBody();
    }

    @Override
    public List<String> getIdProductSimilar(String productId) {
        log.debug("Getting id products similar for " + productId);
        String productSimilarUrl = urlGetProductSimilarId.replace("{productId}", productId );
        List<String> response = null;
        try{
            response = restTemplate.getForEntity(productSimilarUrl, ArrayList.class).getBody();
        }catch (Exception error){
            log.error("http error: " + error.getMessage());
            response = new ArrayList<>();
        }

        return response;

    }
}
