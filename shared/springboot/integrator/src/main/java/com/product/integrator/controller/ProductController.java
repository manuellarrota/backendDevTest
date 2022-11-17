package com.product.integrator.controller;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import com.product.integrator.services.ProductSimilarService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController {

    private static final String RESILIENCE4J_INSTANCE_NAME = "productService";
    private static final String FALLBACK_METHOD = "getProductSimilarFallBack";
    final ProductSimilarService productSimilarService;
    @Autowired
    public ProductController(ProductSimilarService productSimilarService) {
        this.productSimilarService = productSimilarService;
    }

    @ApiOperation(value = "List of similar products to a given one ordered by similarity")
    @GetMapping("{productId}/similar" )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    ResponseEntity<ProductResponseDto> getProductSimilar(@PathVariable String productId){
        log.info("<getProductSimilar> Begin, Validating data for: " + productId);
        if(productId == null){
            log.error(" productId is null.");
            return new ResponseEntity<>( new ProductResponseDto(), HttpStatus.NOT_FOUND);
        }
        List<ProductDto> similarProductsDtoList = productSimilarService.getProductSimilar(productId);
        ResponseEntity<ProductResponseDto> responseEntity;
        if(similarProductsDtoList != null && !similarProductsDtoList.isEmpty()){
            responseEntity = new ResponseEntity<>( new ProductResponseDto(similarProductsDtoList), HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<>( new ProductResponseDto(), HttpStatus.NOT_FOUND);
        }
        log.info("<getProductSimilar> End. ");
        return responseEntity;
    }

    ResponseEntity<ProductResponseDto> getProductSimilarFallBack(@PathVariable String productId, RuntimeException error){
        log.info("fallback "+ productId+ " , " + error);
        return new ResponseEntity<>( new ProductResponseDto(), HttpStatus.NOT_FOUND);
    }

}
