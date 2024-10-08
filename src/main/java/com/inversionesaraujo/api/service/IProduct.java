package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.model.entity.Product;

public interface IProduct {
    List<Product> listAll();

    List<Product> search(String name, String description);

    List<Product> findByCategory(Integer categoryId);

    List<Product> findByPrice(Double priceMin, Double priceMax);

    List<Product> findByCategoryAndPrice(Category category, Double priceMin, Double priceMax);

    List<Product> findByPriceLessThan(Double price);

    List<Product> findByPriceGreaterThan(Double price);

    List<Product> findByCategoryAndPriceLessThan(Category category, Double price);

    List<Product> findByCategoryAndPriceGreaterThan(Category category, Double price);

    Product save(Product product);

    Product findById(Integer id);

    void delete(Product product);
}
