package com.inversionesaraujo.api.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.entity.SortDirection;

public interface IProduct {
    Page<Product> filterProducts(
        Double minPrice, Double maxPrice, Integer categoryId, 
        Integer page, Integer size, SortDirection direction
    );

    Page<Product> search(String name, String description, String brand, Integer page);

    Product save(Product product);

    Product findById(Integer id);

    void delete(Product product);
}
