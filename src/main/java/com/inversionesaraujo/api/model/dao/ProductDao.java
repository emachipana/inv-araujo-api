package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.model.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<Product> findByPriceBetween(Double priceMin, Double priceMax);

    List<Product> findByCategoryAndPriceBetween(Category category, Double priceMin, Double priceMax);

    List<Product> findByPriceLessThanEqual(Double price);

    List<Product> findByPriceGreaterThanEqual(Double price);

    List<Product> findByCategoryAndPriceLessThanEqual(Category category, Double price);

    List<Product> findByCategoryAndPriceGreaterThanEqual(Category category, Double price);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId OR p.category.category.id = :categoryId")
    List<Product> findByCategory(@Param("categoryId") Integer categoryId);
}
