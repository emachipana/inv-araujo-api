package com.inversionesaraujo.api.business.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String unit;
    private Double price;
    private Double purchasePrice;
    private Integer stock;
    private CategoryDTO category;
    private Boolean isActive;
    private List<ProductImageDTO> images;
    private DiscountDTO discount;

    public static ProductDTO toDTO(Product product) {
        if(product == null) return null;

        return ProductDTO
            .builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .brand(product.getBrand())
            .unit(product.getUnit())
            .price(product.getPrice())
            .purchasePrice(product.getPurchasePrice())
            .stock(product.getStock())
            .category(CategoryDTO.toDTO(product.getCategory(), 0))
            .isActive(product.getIsActive())
            .images(ProductImageDTO.toDTOList(product.getImages()))
            .discount(DiscountDTO.toDTO(product.getDiscount()))
            .build();
    }

    public static Product toEntity(ProductDTO product) {
        if(product == null) return null;

        return Product
            .builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .brand(product.getBrand())
            .unit(product.getUnit())
            .price(product.getPrice())
            .purchasePrice(product.getPurchasePrice())
            .stock(product.getStock() == null ? 0 : product.getStock())
            .category(CategoryDTO.toEntity(product.getCategory()))
            .isActive(product.getIsActive())
            .build();
    }

    public static Page<ProductDTO> toPageableDTO(Page<Product> products) {
        return products.map(ProductDTO::toDTO);
    }
}
