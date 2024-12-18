package com.inversionesaraujo.api.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCase
        (String name, String description, String brand, Pageable pageable);
}
