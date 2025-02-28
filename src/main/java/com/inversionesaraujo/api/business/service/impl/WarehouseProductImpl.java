package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.WarehouseProductDTO;
import com.inversionesaraujo.api.business.service.IWarehouseProduct;
import com.inversionesaraujo.api.model.WarehouseProduct;
import com.inversionesaraujo.api.repository.WarehouseProductRepository;

@Service
public class WarehouseProductImpl implements IWarehouseProduct {
    @Autowired
    private WarehouseProductRepository itemRepo;

    @Override
    public WarehouseProductDTO save(WarehouseProductDTO item) {
        WarehouseProduct itemSaved = itemRepo.save(WarehouseProductDTO.toEntity(item));

        return WarehouseProductDTO.toDTO(itemSaved);
    }

    @Override
    public WarehouseProductDTO findById(Long id) {
        WarehouseProduct item = itemRepo.findById(id).orElseThrow(() -> new DataAccessException("El item no existe") {});

        return WarehouseProductDTO.toDTO(item);
    }

    @Override
    public void delete(Long id) {
        itemRepo.deleteById(id);
    }
    
}
