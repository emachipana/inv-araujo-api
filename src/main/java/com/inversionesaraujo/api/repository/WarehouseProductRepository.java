package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.WarehouseProduct;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
    @Query("SELECT COUNT(wp.product.id) FROM WarehouseProduct wp WHERE wp.warehouse.id = :warehouseId")
    Integer countProductsByWarehouse(@Param("warehouseId") Long warehouseId);
}
