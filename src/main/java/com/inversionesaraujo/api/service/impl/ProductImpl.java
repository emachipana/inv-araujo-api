package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ProductDao;
import com.inversionesaraujo.api.model.entity.Category;
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

    @Override
    public List<Product> search(String name, String description) {
        return productDao.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, description);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productDao.findByCategory(category);
    }

    @Override
    public List<Product> findByPrice(Double priceMin, Double priceMax) {
        return productDao.findByPriceBetween(priceMin, priceMax);
    }

    @Override
    public List<Product> findByCategoryAndPrice(Category category, Double priceMin, Double priceMax) {
        return productDao.findByCategoryAndPriceBetween(category, priceMin, priceMax);
    }

    @Override
    public List<Product> findByPriceLessThan(Double price) {
        return productDao.findByPriceLessThanEqual(price);
    }

    @Override
    public List<Product> findByPriceGreaterThan(Double price) {
        return productDao.findByPriceGreaterThanEqual(price);
    }

    @Override
    public List<Product> findByCategoryAndPriceLessThan(Category category, Double price) {
        return productDao.findByCategoryAndPriceLessThanEqual(category, price);
    }

    @Override
    public List<Product> findByCategoryAndPriceGreaterThan(Category category, Double price) {
        return productDao.findByCategoryAndPriceGreaterThanEqual(category, price);
    }

    @Override
    public List<Product> findBySubCategories(Category category) {
        return productDao.findBySubCategories(category.getId());
    }
}
