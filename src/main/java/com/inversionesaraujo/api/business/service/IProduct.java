package com.inversionesaraujo.api.business.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;

public interface IProduct {
    Page<Product> filterProducts(
        Double minPrice, Double maxPrice, Integer categoryId, 
        Integer page, Integer size, SortBy sort, SortDirection direction
    );

    Page<Product> search(String name, String description, String brand, Integer page);

    Product save(Product product);

    Product findById(Integer id);

    void delete(Product product);
}
