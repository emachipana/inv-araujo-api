package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.ProductImageDTO;

public interface IProductImage {
    ProductImageDTO save(ProductImageDTO image);

    ProductImageDTO findById(Long id);

    void delete(Long id);
}
