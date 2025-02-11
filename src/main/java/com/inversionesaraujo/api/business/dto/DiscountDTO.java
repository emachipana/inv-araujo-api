package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Discount;
import com.inversionesaraujo.api.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountDTO {
    private Long id;
    private Long productId;
    private Double price;
    private Integer percentage;

    public static DiscountDTO toDTO(Discount discount) {
        if(discount == null) return null;

        return DiscountDTO
            .builder()
            .id(discount.getId())
            .productId(discount.getProduct().getId())
            .price(discount.getPrice())
            .percentage(discount.getPercentage())
            .build();
    }

    public static Discount toEntity(DiscountDTO discount) {
        if(discount == null) return null;

        Product product = new Product();
        product.setId(discount.getProductId());
        
        return Discount
            .builder()
            .id(discount.getId())
            .product(product)
            .price(discount.getPrice())
            .percentage(discount.getPercentage())
            .build();
    }
}
