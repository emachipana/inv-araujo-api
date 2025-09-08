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

    @Query(value = """
        SELECT * FROM products
        WHERE category_id = :categoryId AND id <> :productId 
        ORDER BY RANDOM() 
        LIMIT 4
        """, nativeQuery = true)
    List<Product> findRelatedProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
    
    @Query(value = """
        SELECT * FROM products 
        WHERE id <> :productId 
        ORDER BY RANDOM() 
        LIMIT :limit
        """, nativeQuery = true)
    List<Product> findRandomProducts(@Param("productId") Long productId, @Param("limit") int limit);

    Boolean existsByNameIgnoreCaseContaining(String name);        
}
