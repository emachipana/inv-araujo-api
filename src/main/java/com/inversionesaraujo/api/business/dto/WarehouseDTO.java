package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Warehouse;
import com.inversionesaraujo.api.model.WarehouseProduct;
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
    private Double latitude;
    private Double longitude;

    public static WarehouseDTO toDTO(Warehouse warehouse, Integer products) {
        if(warehouse == null) return null;

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
            .latitude(warehouse.getLatitude())
            .longitude(warehouse.getLongitude())
            .build();
    }
    
    public static Warehouse toEntity(WarehouseDTO warehouse) {
        if(warehouse == null) return null;

        return Warehouse
            .builder()
            .id(warehouse.getId())
            .name(warehouse.getName())
            .department(warehouse.getDepartment())
            .province(warehouse.getProvince())
            .district(warehouse.getDistrict())
            .address(warehouse.getAddress())
            .ref(warehouse.getRef())
            .latitude(warehouse.getLatitude())
            .longitude(warehouse.getLongitude())
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

    public static List<WarehouseDTO> toDTOList(List<Warehouse> warehouses, WarehouseProductRepository repo, Long productId) {
        return warehouses
            .stream()
            .map(warehouse -> {
                Integer products = 0;
                if(repo != null) {
                    WarehouseProduct item = repo.findByWarehouseIdAndProductId(warehouse.getId(), productId);
                    products = item.getQuantity();
                }
                return WarehouseDTO.toDTO(warehouse, products);
            })
            .collect(Collectors.toList());
    }
}
