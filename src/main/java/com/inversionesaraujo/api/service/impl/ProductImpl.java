package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ProductDao;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.spec.ProductSpecifications;
import com.inversionesaraujo.api.service.IProduct;

@Service
public class ProductImpl implements IProduct {
    @Autowired
    private ProductDao productDao;

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
    public List<Product> search(String name, String description) {
        return productDao.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, description);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> filterProducts(Double minPrice, Double maxPrice, Integer categoryId, Integer page, Integer size) {
        Specification<Product> spec = Specification.where(
            ProductSpecifications.priceGreaterThanOrEqual(minPrice)
            .and(ProductSpecifications.priceLessThanOrEqual(maxPrice))
            .and(ProductSpecifications.belongsToCategory(categoryId))
        );

        Pageable pageable = PageRequest.of(page, size);
        return productDao.findAll(spec, pageable);
    }
}
