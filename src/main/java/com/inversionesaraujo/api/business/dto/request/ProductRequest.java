package com.inversionesaraujo.api.business.dto.request;

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
    private Double purchasePrice;
    private Boolean isActive;
    private Integer stock;
    private String name;
    private String description;
    private String brand;
    private String unit;
}
