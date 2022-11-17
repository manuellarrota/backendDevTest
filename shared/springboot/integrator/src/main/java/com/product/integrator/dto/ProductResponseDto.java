package com.product.integrator.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@ApiModel("Product list response.")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    List<ProductDto> similarProducts = new ArrayList<>();
}
