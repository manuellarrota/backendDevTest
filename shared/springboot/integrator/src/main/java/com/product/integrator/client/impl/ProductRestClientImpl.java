package com.product.integrator.client.impl;

import com.product.integrator.client.ProductRestClient;
import com.product.integrator.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
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

    private final String urlGetProductById;
    private final String urlGetProductSimilarId;
    private final RestTemplate restTemplate;

    public ProductRestClientImpl(String urlGetProductById, String urlGetProductSimilarId, RestTemplate restTemplate) {
        this.urlGetProductById = urlGetProductById;
        this.urlGetProductSimilarId = urlGetProductSimilarId;
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductDto getProductById(String productId) {
        log.debug("getting product detail for " + productId);
        String productUrl = urlGetProductById + productId;
        if(productId == null){
            log.error("productId is null " );
            return new ProductDto();
        }
        if(urlGetProductById == null){
            log.error("urlGetProductById is null " );
            return new ProductDto();
        }
        ResponseEntity<ProductDto> response;
        try{
            response = restTemplate.getForEntity(productUrl, ProductDto.class);
            if ( response == null || response.getStatusCode() != HttpStatus.OK){
                log.debug("error consuming similar ids: " + response);
                return new ProductDto();
            }
        }catch (RestClientException error){
            log.error("http rest client error: " + error.getMessage());
            return new ProductDto();
        }
        log.debug("getting product detail response:" + response);
        return response.getBody();
    }


    @Override
    public List<String> getIdProductSimilar(String productId) {
        log.debug("getting id products similar for " + productId);
        if(productId == null){
            log.error("productId is null " );
            return new ArrayList<>();
        }
        if(urlGetProductSimilarId == null){
            log.error("urlGetProductById is null " );
            return new ArrayList<>();
        }
        String productSimilarUrl = urlGetProductSimilarId.replace("{productId}", productId );
        List<String> response;
        try{
            response = restTemplate.getForEntity(productSimilarUrl, List.class).getBody();
        }catch (Exception error){
            log.error("http rest client error: " + error.getMessage());
            response = new ArrayList<>();
        }
        return response;
    }
}
