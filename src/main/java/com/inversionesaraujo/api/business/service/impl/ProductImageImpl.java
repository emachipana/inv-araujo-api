package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IProductImage;
import com.inversionesaraujo.api.model.ProductImage;
import com.inversionesaraujo.api.repository.ProductImageRepository;

@Service
public class ProductImageImpl implements IProductImage {
    @Autowired
    private ProductImageRepository productImageRepo;

    @Transactional
    @Override
    public ProductImage save(ProductImage image) {
        return productImageRepo.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductImage findById(Integer id) {
        return productImageRepo.findById(id).orElseThrow(() -> new DataAccessException("La imagen del producto no existe") {});
    }

    @Transactional
    @Override
    public void delete(ProductImage image) {
        productImageRepo.delete(image);
    }
}
