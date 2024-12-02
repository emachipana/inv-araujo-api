package com.inversionesaraujo.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.Product;

public interface IProduct {
    Page<Product> filterProducts(Double minPrice, Double maxPrice, Integer categoryId, Integer page, Integer size);

    List<Product> search(String name, String description, String brand);

    Product save(Product product);

    Product findById(Integer id);

    void delete(Product product);
}
