package com.product.integrator.controller;

import com.product.integrator.dto.ProductDto;
import com.product.integrator.dto.ProductResponseDto;
import com.product.integrator.services.ProductSimilarService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

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
    ProductResponseDto getProductSimilars(@PathParam("productId") Long productId){
        ArrayList<ProductDto> similarProductsDtoList = productSimilarService.getProductSimilar(productId);
        return  new ProductResponseDto(similarProductsDtoList);
    }
}
