package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {}
