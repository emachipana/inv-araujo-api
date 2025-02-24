package com.inversionesaraujo.api.business.dto;

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

    public static WarehouseProductDTO toDTO(WarehouseProduct item) {
        return WarehouseProductDTO
            .builder()
            .id(item.getId())
            .quantity(item.getQuantity())
            .productId(item.getProduct().getId())
            .warehouseId(item.getWarehouse().getId())
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
            .build();
    }
}
