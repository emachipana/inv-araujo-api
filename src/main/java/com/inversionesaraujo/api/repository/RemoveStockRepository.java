package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.RemoveStock;

public interface RemoveStockRepository extends JpaRepository<RemoveStock, Long> {
    List<RemoveStock> findByProductId(Long productId);
}
