package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Product;

public interface IProduct {
    List<Product> listAll();

    Product save(Product product);

    Product findById(Integer id);

    void delete(Product product);

    boolean ifExists(Integer id);
}
