package com.product.integrator.controller;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import com.product.integrator.services.ProductSimilarService;
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

    ProductSimilarService productSimilarService;
    @Autowired
    public ProductController(ProductSimilarService productSimilarService) {
        this.productSimilarService = productSimilarService;
    }

    @ApiOperation(value = "List of similar products to a given one ordered by similarity")
    @GetMapping("{productId}/similar")
    ResponseEntity<ProductResponseDto> getProductSimilar(@PathVariable String productId){
        List<ProductDto> similarProductsDtoList = productSimilarService.getProductSimilar(productId);
        ResponseEntity<ProductResponseDto> responseEntity;
        if(!similarProductsDtoList.isEmpty()){
            responseEntity = new ResponseEntity<>( new ProductResponseDto(similarProductsDtoList), HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<>( new ProductResponseDto(), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
