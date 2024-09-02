package com.inversionesaraujo.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Integer categoryId;
    private Double price;
    private Boolean isActive;
    private Integer stock;
    private String name;
    private String description;
}
