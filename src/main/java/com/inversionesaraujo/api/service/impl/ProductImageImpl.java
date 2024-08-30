package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ProductImageDao;
import com.inversionesaraujo.api.model.entity.ProductImage;
import com.inversionesaraujo.api.service.IProductImage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageImpl implements IProductImage {
    private ProductImageDao productImageDao;

    @Transactional
    @Override
    public ProductImage save(ProductImage image) {
        return productImageDao.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductImage findById(Integer id) {
        return productImageDao.findById(id).orElseThrow(() -> new DataAccessException("La imagen del producto no existe") {});
    }

    @Transactional
    @Override
    public void delete(ProductImage image) {
        productImageDao.delete(image);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return productImageDao.existsById(id);
    }
}
