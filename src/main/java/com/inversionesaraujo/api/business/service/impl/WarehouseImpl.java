package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.service.IWarehouse;
import com.inversionesaraujo.api.model.Warehouse;
import com.inversionesaraujo.api.repository.WarehouseProductRepository;
import com.inversionesaraujo.api.repository.WarehouseRepository;

@Service
public class WarehouseImpl implements IWarehouse {
    @Autowired
    private WarehouseRepository warehouseRepo;
    @Autowired
    private WarehouseProductRepository itemRepo;

    @Transactional(readOnly = true)
    @Override
    public List<WarehouseDTO> listAll() {
        List<Warehouse> warehouses = warehouseRepo.findAll();

        return WarehouseDTO.toDTOList(warehouses, itemRepo);
    }

    @Transactional
    @Override
    public WarehouseDTO save(WarehouseDTO warehouse) {
        Warehouse warehouseSaved = warehouseRepo.save(WarehouseDTO.toEntity(warehouse));
        Integer products = 0;
        if(warehouse.getId() != null) products = itemRepo.countProductsByWarehouse(warehouse.getId());

        return WarehouseDTO.toDTO(warehouseSaved, products);
    }

    @Transactional(readOnly = true)
    @Override
    public WarehouseDTO findById(Long id) {
        Warehouse warehouse = warehouseRepo.findById(id).orElseThrow(() -> new DataAccessException("El almacén no existe") {});
        Integer products = itemRepo.countProductsByWarehouse(id);

        return WarehouseDTO.toDTO(warehouse, products);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        warehouseRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public WarehouseDTO findByName(String name) {
        Warehouse warehouse = warehouseRepo.findByName(name);
        
        return WarehouseDTO.toDTO(warehouse, 0);
    }    
}
