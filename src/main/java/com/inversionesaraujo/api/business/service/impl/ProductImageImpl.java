package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.ProductImageDTO;
import com.inversionesaraujo.api.business.service.IProductImage;
import com.inversionesaraujo.api.model.ProductImage;
import com.inversionesaraujo.api.repository.ProductImageRepository;

@Service
public class ProductImageImpl implements IProductImage {
    @Autowired
    private ProductImageRepository productImageRepo;

    @Transactional
    @Override
    public ProductImageDTO save(ProductImageDTO image) {
        ProductImage imageSaved = productImageRepo.save(ProductImageDTO.toEntity(image));

        return ProductImageDTO.toDTO(imageSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductImageDTO findById(Long id) {
        ProductImage image = productImageRepo.findById(id).orElseThrow(() -> new DataAccessException("La imagen del producto no existe") {});

        return ProductImageDTO.toDTO(image);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productImageRepo.deleteById(id);
    }
}
