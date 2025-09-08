package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.business.spec.ProductSpecifications;
import com.inversionesaraujo.api.model.Product;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Warehouse;
import com.inversionesaraujo.api.repository.ProductRepository;
import com.inversionesaraujo.api.repository.WarehouseProductRepository;
import com.inversionesaraujo.api.repository.WarehouseRepository;

@Service
public class ProductImpl implements IProduct {
    @Autowired
    private WarehouseProductRepository warehouseProductRepository;
    @Autowired
    private ProductRepository productRepo;
    @Autowired 
    private WarehouseRepository warehouseRepo;

    @Transactional
    @Override
    public ProductDTO save(ProductDTO product) {
        Product productSaved = productRepo.save(ProductDTO.toEntity(product)); 

        return ProductDTO.toDTO(productSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto no existe") {});

        return ProductDTO.toDTO(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDTO> search(String name, String description, String brand, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Product> products = productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrBrandContainingIgnoreCase
            (name, description, brand, pageable);

        return ProductDTO.toPageableDTO(products); 
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDTO> filterProducts(
        Double minPrice, Double maxPrice, Long categoryId, Integer page, 
        Integer size, SortBy sort, SortDirection direction, String categoryName,
        Integer stockLessThan, Boolean withDiscount, Boolean activeProducts
    ) {
        Specification<Product> spec = Specification.where(
            ProductSpecifications.priceGreaterThanOrEqual(minPrice)
            .and(ProductSpecifications.priceLessThanOrEqual(maxPrice))
            .and(ProductSpecifications.belongsToCategory(categoryId))
            .and(ProductSpecifications.findByCategoryName(categoryName))
            .and(ProductSpecifications.hasDiscount(withDiscount))
            .and(ProductSpecifications.stockLessThanOrEqual(stockLessThan))
            .and(ProductSpecifications.activeProducts(activeProducts))
        );

        Pageable pageable;
        if(sort != null) {
            Sort sorted = Sort.by(Sort.Direction.fromString(direction.toString()), sort.toString());
            pageable = PageRequest.of(page, size, sorted);
        }else {
            pageable = PageRequest.of(page, size);
        }

        Page<Product> products = productRepo.findAll(spec, pageable); 

        return ProductDTO.toPageableDTO(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WarehouseDTO> getWarehouses(Long productId) {
        List<Warehouse> warehouses = warehouseRepo.findByProductId(productId);

        return WarehouseDTO.toDTOList(warehouses, warehouseProductRepository, productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> findRelatedProducts(Long productId, Long categoryId) {
        List<Product> relatedProducts = productRepo.findRelatedProducts(categoryId, productId);

        if (relatedProducts.size() < 4) {
            List<Product> randomProducts = productRepo.findRandomProducts(productId, 4 - relatedProducts.size());
            relatedProducts.addAll(randomProducts);
        }

        return ProductDTO.toDTOList(relatedProducts);
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepo.existsByNameIgnoreCaseContaining(name);
    }
}
