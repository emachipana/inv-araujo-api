package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.WarehouseChange;

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
public class WarehouseChangeDTO {
    private Long id;
    private String reason;
    private WarehouseDTO fromWarehouse;
    private WarehouseDTO toWarehouse;
    private EmployeeDTO employee;
    private Integer quantity;
    private ProductDTO product;

    public static WarehouseChangeDTO toDTO(WarehouseChange item) {
        return WarehouseChangeDTO
            .builder()
            .id(item.getId())
            .reason(item.getReason())
            .fromWarehouse(WarehouseDTO.toDTO(item.getFromWarehouse(), 0))
            .toWarehouse(WarehouseDTO.toDTO(item.getToWarehouse(), 0))
            .employee(EmployeeDTO.toDTO(item.getEmployee()))
            .quantity(item.getQuantity())
            .product(ProductDTO.toDTO(item.getProduct()))
            .build();
    }

    public static WarehouseChange toEntity(WarehouseChangeDTO item) {
        return WarehouseChange
            .builder()
            .id(item.getId())
            .reason(item.getReason())
            .fromWarehouse(WarehouseDTO.toEntity(item.getFromWarehouse()))
            .toWarehouse(WarehouseDTO.toEntity(item.getToWarehouse()))
            .employee(EmployeeDTO.toEntity(item.getEmployee()))
            .quantity(item.getQuantity())
            .product(ProductDTO.toEntity(item.getProduct()))
            .build();
    }
}
