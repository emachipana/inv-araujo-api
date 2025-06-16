package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Category;
import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.ProductUnit;

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
    private ProductUnit unit;
    private Double price;
    private Double purchasePrice;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
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
            .categoryId(product.getCategory().getId())
            .categoryName(product.getCategory().getName())
            .isActive(product.getIsActive())
            .images(ProductImageDTO.toDTOList(product.getImages()))
            .discount(DiscountDTO.toDTO(product.getDiscount()))
            .build();
    }

    public static Product toEntity(ProductDTO product) {
        if(product == null) return null;

        Category category = new Category();
        category.setId(product.getCategoryId());
        category.setName(product.getCategoryName());

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
            .category(category)
            .isActive(product.getIsActive())
            .build();
    }

    public static Page<ProductDTO> toPageableDTO(Page<Product> products) {
        return products.map(ProductDTO::toDTO);
    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products
            .stream()
            .map(ProductDTO::toDTO)
            .collect(Collectors.toList());
    }
}
