package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.ProductImage;

public interface IProductImage {
    ProductImage save(ProductImage image);

    ProductImage findById(Integer id);

    void delete(ProductImage image);

    boolean ifExists(Integer id);
}
