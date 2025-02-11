package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.ProductImage;

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
public class ProductImageDTO {
    private Long id;
    private Long productId;
    private ImageDTO image;

    public static ProductImageDTO toDTO(ProductImage productImage) {
        if(productImage == null) return null;

        return ProductImageDTO
            .builder()
            .id(productImage.getId())
            .productId(productImage.getProduct().getId())
            .image(ImageDTO.toDTO(productImage.getImage()))
            .build();
    }

    public static ProductImage toEntity(ProductImageDTO productImage) {
        if(productImage == null) return null;

        Product product = new Product();
        product.setId(productImage.getProductId());

        return ProductImage
            .builder()
            .id(productImage.getId())
            .image(ImageDTO.toEntity(productImage.getImage()))
            .product(product)
            .build();
    }

    public static List<ProductImageDTO> toDTOList(List<ProductImage> images) {
        if(images == null) return null;

        return images
            .stream()
            .map(ProductImageDTO::toDTO)
            .collect(Collectors.toList());
    }
}
