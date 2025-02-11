package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
    private Long id;
    private String name;
    private String department;
    private String province;
    private String district;
    private String address;
    private String ref;
    private Integer countProducts;

    public static WarehouseDTO toDTO(Warehouse warehouse, Integer products) {
        return WarehouseDTO
            .builder()
            .id(warehouse.getId())
            .name(warehouse.getName())
            .department(warehouse.getDepartment())
            .province(warehouse.getProvince())
            .district(warehouse.getDistrict())
            .address(warehouse.getAddress())
            .ref(warehouse.getRef())
            .countProducts(products)
            .build();
    }
    
    public static Warehouse toEntity(WarehouseDTO warehouse) {
        return Warehouse
            .builder()
            .id(warehouse.getId())
            .name(warehouse.getName())
            .department(warehouse.getDepartment())
            .province(warehouse.getProvince())
            .district(warehouse.getDistrict())
            .address(warehouse.getAddress())
            .ref(warehouse.getRef())
            .build();
    }    
}
