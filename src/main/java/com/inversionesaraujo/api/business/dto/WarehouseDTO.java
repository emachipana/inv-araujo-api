package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Warehouse;
import com.inversionesaraujo.api.repository.WarehouseProductRepository;

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
    private Integer products;

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
            .products(products)
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

    public static List<WarehouseDTO> toDTOList(List<Warehouse> warehouses, WarehouseProductRepository repo) {
        return warehouses
            .stream()
            .map(warehouse -> {
                Integer products = 0;
                if(repo != null) products = repo.countProductsByWarehouse(warehouse.getId());
                return WarehouseDTO.toDTO(warehouse, products);
            })
            .collect(Collectors.toList());
    }
}
