package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;

import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.Warehouse;
import com.inversionesaraujo.api.model.WarehouseProduct;

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
public class WarehouseProductDTO {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Long warehouseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static WarehouseProductDTO toDTO(WarehouseProduct item) {
        if(item == null) return null;

        return WarehouseProductDTO
            .builder()
            .id(item.getId())
            .quantity(item.getQuantity() != null ? item.getQuantity() : 0)
            .productId(item.getProduct() != null ? item.getProduct().getId() : null)
            .warehouseId(item.getWarehouse() != null ? item.getWarehouse().getId() : null)
            .createdAt(item.getCreatedAt())
            .updatedAt(item.getUpdatedAt())
            .build();
    }

    public static WarehouseProduct toEntity(WarehouseProductDTO item) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(item.getWarehouseId());

        Product product = new Product();
        product.setId(item.getProductId());

        return WarehouseProduct
            .builder()
            .id(item.getId())
            .quantity(item.getQuantity())
            .product(product)
            .warehouse(warehouse)
            .createdAt(item.getCreatedAt())
            .updatedAt(item.getUpdatedAt())
            .build();
    }
}
