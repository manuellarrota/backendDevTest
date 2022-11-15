package com.product.integrator.infraestructure.controller;

import com.product.integrator.infraestructure.dto.ProductResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController {
    @ApiOperation(value = "Get similar products")
    @GetMapping("{productId}/similar")
    ProductResponseDto getSimilars(){
        return  new ProductResponseDto();
    }
}
