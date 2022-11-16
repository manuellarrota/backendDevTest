package com.product.integrator.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Product detail")
@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto implements Comparable<ProductDto> {
    private String id;
    private String name;
    private double price;
    private boolean availability;


    @Override
    public int compareTo(ProductDto o) {
        return o.id.compareTo(this.id);
    }
}
