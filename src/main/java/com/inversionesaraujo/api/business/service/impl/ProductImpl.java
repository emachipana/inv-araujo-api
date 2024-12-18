package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.business.spec.ProductSpecifications;
import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.ProductRepository;

@Service
public class ProductImpl implements IProduct {
    @Autowired
    private ProductRepository productRepo;

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Integer id) {
        return productRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto no existe") {});
    }

    @Transactional
    @Override
    public void delete(Product product) {
        productRepo.delete(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> search(String name, String description, String brand, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCase
            (name, description, brand, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> filterProducts(Double minPrice, Double maxPrice, Integer categoryId, Integer page, Integer size, SortDirection direction) {
        Specification<Product> spec = Specification.where(
            ProductSpecifications.priceGreaterThanOrEqual(minPrice)
            .and(ProductSpecifications.priceLessThanOrEqual(maxPrice))
            .and(ProductSpecifications.belongsToCategory(categoryId))
        );

        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "price");
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

        return productRepo.findAll(spec, pageable);
    }
}
