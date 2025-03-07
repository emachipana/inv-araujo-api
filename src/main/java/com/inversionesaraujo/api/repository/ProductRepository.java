package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.inversionesaraujo.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCase
        (String name, String description, String brand, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.discount IS NOT NULL ORDER BY p.id DESC")
    List<Product> findByDiscountProducts(Pageable pageable);
}
