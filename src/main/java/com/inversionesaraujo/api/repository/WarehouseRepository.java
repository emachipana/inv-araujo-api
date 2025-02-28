package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value = "SELECT DISTINCT w.* FROM warehouses w " +
               "JOIN warehouse_products wp ON w.id = wp.warehouse_id " +
               "WHERE wp.product_id = :productId", nativeQuery = true)
    List<Warehouse> findByProductId(@Param("productId") Long productId);
}
