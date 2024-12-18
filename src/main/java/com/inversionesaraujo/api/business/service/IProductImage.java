package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.ProductImage;

public interface IProductImage {
    ProductImage save(ProductImage image);

    ProductImage findById(Integer id);

    void delete(ProductImage image);
}
