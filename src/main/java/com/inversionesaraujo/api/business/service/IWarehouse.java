package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.WarehouseDTO;

public interface IWarehouse {
    List<WarehouseDTO> listAll();

    WarehouseDTO save(WarehouseDTO warehouse);

    WarehouseDTO findById(Long id);

    WarehouseDTO findByName(String name);

    void delete(Long id);
}
