package com.inversionesaraujo.api.business.dto;

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
    private ProductDTO product;
    private Long warehouseId;

    public static WarehouseProductDTO toDTO(WarehouseProduct item) {
        return WarehouseProductDTO
            .builder()
            .id(item.getId())
            .quantity(item.getQuantity())
            .product(ProductDTO.toDTO(item.getProduct()))
            .warehouseId(item.getWarehouse().getId())
            .build();
    }

    public static WarehouseProduct toEntity(WarehouseProductDTO item) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(item.getWarehouseId());
        
        return WarehouseProduct
            .builder()
            .id(item.getId())
            .quantity(item.getQuantity())
            .product(ProductDTO.toEntity(item.getProduct()))
            .warehouse(warehouse)
            .build();
    }
}
