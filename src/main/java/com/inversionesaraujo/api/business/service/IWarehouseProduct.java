package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.WarehouseProductDTO;

public interface IWarehouseProduct {
    WarehouseProductDTO save(WarehouseProductDTO item);

    WarehouseProductDTO findById(Long id);

    void delete(Long id);
}
