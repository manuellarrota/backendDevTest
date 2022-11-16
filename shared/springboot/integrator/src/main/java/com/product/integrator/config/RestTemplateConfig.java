package com.product.integrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${url.get_product_by_id}")
    private String urlGetProductById;
    @Value("${url.get_product_similar_id}")
    private String urlGetProductSimilarId;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public String urlGetProductById(){
        return urlGetProductById;
    }
    @Bean
    public String urlGetProductSimilarId(){
        return urlGetProductSimilarId;
    }
}
