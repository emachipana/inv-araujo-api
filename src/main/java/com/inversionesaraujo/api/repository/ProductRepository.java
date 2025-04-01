package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCase
        (String name, String description, String brand, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id <> :productId ORDER BY RAND() LIMIT 4")
    List<Product> findRelatedProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id <> :productId ORDER BY RAND() LIMIT :limit")
    List<Product> findRandomProducts( @Param("productId") Long productId, @Param("limit") int limit);
}
