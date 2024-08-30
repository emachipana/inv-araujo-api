package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ProductDao;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.service.IProduct;

@Service
public class ProductImpl implements IProduct {
    @Autowired
    private ProductDao productDao;

    @Transactional(readOnly = true)
    @Override
    public List<Product> listAll() {
        return productDao.findAll();
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Integer id) {
        return productDao.findById(id).orElseThrow(() -> new DataAccessException("El producto no existe") {});
    }

    @Transactional
    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return productDao.existsById(id);
    }
}
