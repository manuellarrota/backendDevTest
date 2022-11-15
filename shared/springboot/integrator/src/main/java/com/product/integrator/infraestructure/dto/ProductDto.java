package com.product.integrator.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    public String id;
    public String name;
    public double price;
    public boolean availability;
}
