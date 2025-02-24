package com.inversionesaraujo.api.business.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;

public interface IProduct {
    Page<ProductDTO> filterProducts(
        Double minPrice, Double maxPrice, Integer categoryId, 
        Integer page, Integer size, SortBy sort, SortDirection direction
    );

    Page<ProductDTO> search(String name, String description, String brand, Integer page);

    ProductDTO save(ProductDTO product);

    ProductDTO findById(Long id);

    void delete(Long id);

    List<WarehouseDTO> getWarehouses(Long productId);
}
